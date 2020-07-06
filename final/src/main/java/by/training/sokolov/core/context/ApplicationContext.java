package by.training.sokolov.core.context;

import by.training.sokolov.command.*;
import by.training.sokolov.command.order.*;
import by.training.sokolov.db.*;
import by.training.sokolov.entity.category.dao.DishCategoryDao;
import by.training.sokolov.entity.category.dao.DishCategoryDaoImpl;
import by.training.sokolov.entity.category.service.DishCategoryService;
import by.training.sokolov.entity.category.service.DishCategoryServiceImpl;
import by.training.sokolov.entity.deliveryaddress.dao.DeliveryAddressDao;
import by.training.sokolov.entity.deliveryaddress.dao.DeliveryAddressDaoImpl;
import by.training.sokolov.entity.dish.dao.DishDao;
import by.training.sokolov.entity.dish.dao.DishDaoImpl;
import by.training.sokolov.entity.dish.service.DishService;
import by.training.sokolov.entity.dish.service.DishServiceImpl;
import by.training.sokolov.entity.dishfeedback.dao.DishFeedbackDao;
import by.training.sokolov.entity.dishfeedback.dao.DishFeedbackDaoImpl;
import by.training.sokolov.entity.loyalty.dao.LoyaltyDao;
import by.training.sokolov.entity.loyalty.dao.LoyaltyDaoImpl;
import by.training.sokolov.entity.order.dao.UserOrderDao;
import by.training.sokolov.entity.order.dao.UserOrderDaoImpl;
import by.training.sokolov.entity.order.service.UserOrderService;
import by.training.sokolov.entity.order.service.UserOrderServiceImpl;
import by.training.sokolov.entity.orderfeedback.dao.OrderFeedbackDao;
import by.training.sokolov.entity.orderfeedback.dao.OrderFeedbackDaoImpl;
import by.training.sokolov.entity.orderitem.dao.OrderItemDao;
import by.training.sokolov.entity.orderitem.dao.OrderItemDaoImpl;
import by.training.sokolov.entity.orderitem.service.OrderItemService;
import by.training.sokolov.entity.orderitem.service.OrderItemServiceImpl;
import by.training.sokolov.entity.role.dao.UserRoleDao;
import by.training.sokolov.entity.role.dao.UserRoleDaoImpl;
import by.training.sokolov.entity.user.dao.UserDao;
import by.training.sokolov.entity.user.dao.UserDaoImpl;
import by.training.sokolov.entity.user.service.UserService;
import by.training.sokolov.entity.user.service.UserServiceImpl;
import by.training.sokolov.entity.useraddress.dao.UserAddressDao;
import by.training.sokolov.entity.useraddress.dao.UserAddressDaoImpl;
import by.training.sokolov.entity.wallet.dao.WalletDao;
import by.training.sokolov.entity.wallet.dao.WalletDaoImpl;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static by.training.sokolov.application.constants.ServletName.*;
import static by.training.sokolov.command.constants.CommandType.*;

public class ApplicationContext {

    private static final Logger LOGGER = Logger.getLogger(ApplicationContext.class);
    private static final AtomicBoolean INITIALIZED = new AtomicBoolean(false);
    private static final Lock INITIALIZE_LOCK = new ReentrantLock();
    private static ApplicationContext INSTANCE;
    private final Map<Class<?>, Object> beans = new HashMap<>();
    private ConnectionPool connectionPool;

    private ApplicationContext() {

    }

    public static void initialize() {

        INITIALIZE_LOCK.lock();
        try {
            if (INSTANCE != null && INITIALIZED.get()) {
                throw new IllegalStateException("context has been already initialized");
            } else {
                ApplicationContext context = new ApplicationContext();
                context.init();
                INSTANCE = context;
                INITIALIZED.set(true);
            }

        } finally {
            INITIALIZE_LOCK.unlock();
        }
    }

    public static ApplicationContext getInstance() {

        if (INSTANCE != null && INITIALIZED.get()) {
            return INSTANCE;
        } else {
            throw new IllegalStateException("context wasn't initialized");
        }
    }

    private static <T> InvocationHandler createTransactionalInvocationHandler(TransactionManager transactionManager, T service) {
        return (proxy, method, args) -> {

            Method declaredMethod = service.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
            if (method.isAnnotationPresent(Transactional.class)
                    || declaredMethod.isAnnotationPresent(Transactional.class)) {
                transactionManager.begin();
                try {
                    Object result = method.invoke(service, args);
                    transactionManager.commit();
                    return result;
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                    transactionManager.rollback();
                    throw e;
                }
            } else {
                return method.invoke(service, args);
            }
        };
    }

    static <T> T createProxy(ClassLoader classLoader, InvocationHandler handler, Class<T>... toBeProxied) {
        return (T) Proxy.newProxyInstance(classLoader, toBeProxied, handler);
    }

    private void init() {
        connectionPool = ConnectionPoolImpl.getInstance();

        TransactionManager transactionManager = new TransactionManagerImpl(connectionPool);
        ConnectionManager connectionManager = new ConnectionManagerImpl(connectionPool, transactionManager);

        //DAO
        DishCategoryDao dishCategoryDao = new DishCategoryDaoImpl(connectionManager);
        DeliveryAddressDao deliveryAddressDao = new DeliveryAddressDaoImpl(connectionManager);
        DishDao dishDao = new DishDaoImpl(connectionManager);
        DishFeedbackDao dishFeedbackDao = new DishFeedbackDaoImpl(connectionManager);
        LoyaltyDao loyaltyDao = new LoyaltyDaoImpl(connectionManager);
        UserOrderDao userOrderDao = new UserOrderDaoImpl(connectionManager);
        OrderFeedbackDao orderFeedbackDao = new OrderFeedbackDaoImpl(connectionManager);
        OrderItemDao orderItemDao = new OrderItemDaoImpl(connectionManager);
        UserRoleDao userRoleDao = new UserRoleDaoImpl(connectionManager);
        UserDao userDao = new UserDaoImpl(connectionManager);
        UserAddressDao userAddressDao = new UserAddressDaoImpl(connectionManager);
        WalletDao walletDao = new WalletDaoImpl(connectionManager);

        //service
        DishService dishService = new DishServiceImpl(dishDao, dishCategoryDao);
        OrderItemService orderItemService = new OrderItemServiceImpl(orderItemDao, dishService);
        UserOrderService userOrderService = new UserOrderServiceImpl(userOrderDao, deliveryAddressDao, orderItemService);
        UserService userService = new UserServiceImpl(userDao, userAddressDao, loyaltyDao, walletDao, userRoleDao);
        DishCategoryService dishCategoryService = new DishCategoryServiceImpl(dishCategoryDao);

        //proxy of services
        InvocationHandler dishServiceHandler = createTransactionalInvocationHandler
                (transactionManager, dishService);
        DishService dishProxyService = createProxy(getClass().getClassLoader(),
                dishServiceHandler, DishService.class);

        InvocationHandler userOrderServiceHandler = createTransactionalInvocationHandler
                (transactionManager, userOrderService);
        UserOrderService userOrderProxyService = createProxy
                (getClass().getClassLoader(), userOrderServiceHandler, UserOrderService.class);

        InvocationHandler orderItemServiceHandler = createTransactionalInvocationHandler
                (transactionManager, orderItemService);
        OrderItemService orderItemProxyService = createProxy
                (getClass().getClassLoader(), orderItemServiceHandler, OrderItemService.class);

        InvocationHandler userServiceHandler = createTransactionalInvocationHandler
                (transactionManager, userService);
        UserService userProxyService = createProxy
                (getClass().getClassLoader(), userServiceHandler, UserService.class);

        InvocationHandler dishCategoryServiceHandler = createTransactionalInvocationHandler
                (transactionManager, dishCategoryService);
        DishCategoryService dishCategoryProxyService = createProxy
                (getClass().getClassLoader(), dishCategoryServiceHandler, DishCategoryService.class);

        //commands
        Command createOrderCommand = new CreateOrderCommand(userOrderProxyService);
        Command deleteDishFromOrderCommand = new DeleteDishFromOrderCommand(orderItemProxyService);
        Command orderDishListDisplayCommand = new OrderDishListDisplayCommand(userOrderProxyService, orderItemProxyService, dishCategoryProxyService);
        Command orderItemAddCommand = new OrderItemAddCommand(orderItemProxyService, dishProxyService, userOrderProxyService);
        Command loginSubmitCommand = new LoginSubmitCommand(userProxyService);
        Command registerUserCommand = new RegisterUserCommand(userProxyService);
        Command viewDishMenuCommand = new ViewDishMenuCommand(dishProxyService, dishCategoryProxyService);
        Command orderCheckoutCommand = new OrderCheckoutDisplayCommand(orderItemProxyService, userOrderProxyService);

        //commandFactory
        CommandFactory commandFactory = new CommandFactoryImpl();

        commandFactory.registerCommand(ORDER_BASKET_SERVLET_SWITCH, (request, response) -> ORDER_BASKET_SERVLET);
        commandFactory.registerCommand(MENU_SERVLET_SWITCH, (request, response) -> MENU_SERVLET);
        commandFactory.registerCommand(REGISTER_SERVLET_SWITCH, (request, response) -> USER_REGISTER_SERVLET);
        commandFactory.registerCommand(LOGIN_SERVLET_SWITCH, (request, response) -> LOGIN_SERVLET);
        commandFactory.registerCommand(ORDER_CHECKOUT_SERVLET_SWITCH, ((request, response) -> ORDER_CHECKOUT_SERVLET));
        commandFactory.registerCommand(INDEX, (request, response) -> INDEX_SERVLET);

        commandFactory.registerCommand(DELETE_DISH_FROM_ORDER, deleteDishFromOrderCommand);
        commandFactory.registerCommand(CREATE_ORDER, createOrderCommand);
        commandFactory.registerCommand(VIEW_ORDER_DISH_LIST, orderDishListDisplayCommand);
        commandFactory.registerCommand(ORDER_ITEM_ADD, orderItemAddCommand);
        commandFactory.registerCommand(CHECKOUT_ORDER_FORM_DISPLAY, orderCheckoutCommand);

        commandFactory.registerCommand(VIEW_DISH_MENU, viewDishMenuCommand);

        commandFactory.registerCommand(REGISTER_USER, registerUserCommand);
        commandFactory.registerCommand(LOGIN_SUBMIT, loginSubmitCommand);
        commandFactory.registerCommand(LOGOUT, (request, response) -> {
            request.getSession().invalidate();
            SecurityContext.getInstance().logout(request.getSession().getId());
            return "logout";
        });


        //bean command provider
        beans.put(CommandFactory.class, commandFactory);

        LOGGER.info("application context initialize");
    }

    public void destroy() throws SQLException {
        connectionPool.shutdown();
        beans.clear();
        LOGGER.info("application context destroy");
    }

    public <T> T getBean(Class<T> clazz) {

        return (T) this.beans.get(clazz);
    }
}