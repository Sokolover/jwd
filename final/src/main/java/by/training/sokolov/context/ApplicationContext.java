package by.training.sokolov.context;

import by.training.sokolov.command.Command;
import by.training.sokolov.command.CommandFactory;
import by.training.sokolov.command.CommandFactoryImpl;
import by.training.sokolov.command.category.CreateDishCategoryFormSubmitCommand;
import by.training.sokolov.command.category.DeleteDishCategoryFormDisplayCommand;
import by.training.sokolov.command.category.DeleteDishCategoryFormSubmitCommand;
import by.training.sokolov.command.dish.*;
import by.training.sokolov.command.feedback.CreateDishFeedbackFormDisplayCommand;
import by.training.sokolov.command.feedback.CreateDishFeedbackFormSubmitCommand;
import by.training.sokolov.command.order.*;
import by.training.sokolov.command.user.LoginUserSubmitCommand;
import by.training.sokolov.command.user.RegisterUserSubmitCommand;
import by.training.sokolov.command.wallet.FillUpWalletFormDisplay;
import by.training.sokolov.command.wallet.FillUpWalletFormSubmit;
import by.training.sokolov.core.constants.CommandReturnValues;
import by.training.sokolov.database.connection.*;
import by.training.sokolov.entity.category.dao.DishCategoryDao;
import by.training.sokolov.entity.category.dao.DishCategoryDaoImpl;
import by.training.sokolov.entity.category.service.DishCategoryService;
import by.training.sokolov.entity.category.service.DishCategoryServiceImpl;
import by.training.sokolov.entity.deliveryaddress.dao.DeliveryAddressDao;
import by.training.sokolov.entity.deliveryaddress.dao.DeliveryAddressDaoImpl;
import by.training.sokolov.entity.deliveryaddress.service.DeliveryAddressService;
import by.training.sokolov.entity.deliveryaddress.service.DeliveryAddressServiceImpl;
import by.training.sokolov.entity.dish.dao.DishDao;
import by.training.sokolov.entity.dish.dao.DishDaoImpl;
import by.training.sokolov.entity.dish.service.DishService;
import by.training.sokolov.entity.dish.service.DishServiceImpl;
import by.training.sokolov.entity.dishfeedback.dao.DishFeedbackDao;
import by.training.sokolov.entity.dishfeedback.dao.DishFeedbackDaoImpl;
import by.training.sokolov.entity.dishfeedback.service.DishFeedbackService;
import by.training.sokolov.entity.dishfeedback.service.DishFeedbackServiceImpl;
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
import by.training.sokolov.entity.user.dao.UserAccountDao;
import by.training.sokolov.entity.user.dao.UserAccountDaoImpl;
import by.training.sokolov.entity.user.service.UserService;
import by.training.sokolov.entity.user.service.UserServiceImpl;
import by.training.sokolov.entity.useraddress.dao.UserAddressDao;
import by.training.sokolov.entity.useraddress.dao.UserAddressDaoImpl;
import by.training.sokolov.entity.wallet.dao.WalletDao;
import by.training.sokolov.entity.wallet.dao.WalletDaoImpl;
import by.training.sokolov.entity.wallet.service.WalletService;
import by.training.sokolov.entity.wallet.service.WalletServiceImpl;
import by.training.sokolov.util.JspUtil;
import by.training.sokolov.validation.*;
import by.training.sokolov.validation.cost.Digits;
import by.training.sokolov.validation.cost.MinCost;
import by.training.sokolov.validation.impl.*;
import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static by.training.sokolov.command.CommandType.*;
import static by.training.sokolov.core.constants.JspName.CREATE_CATEGORY_FORM_JSP;
import static by.training.sokolov.core.constants.ServletName.*;

public class ApplicationContext {

    private static final Logger LOGGER = Logger.getLogger(ApplicationContext.class.getName());

    private static final AtomicBoolean INITIALIZED = new AtomicBoolean(false);
    private static final Lock INITIALIZE_LOCK = new ReentrantLock();
    private static ApplicationContext instance;
    private final Map<Class<?>, Object> beans = new HashMap<>();
    private ConnectionPool connectionPool;

    private ApplicationContext() {

    }

    public static void initialize() {

        INITIALIZE_LOCK.lock();
        try {
            if (instance != null && INITIALIZED.get()) {

                String message = "Context has been already initialized";
                LOGGER.error(message);
                throw new IllegalStateException(message);

            } else {

                ApplicationContext context = new ApplicationContext();
                context.init();
                instance = context;
                INITIALIZED.set(true);
                LOGGER.info("Application context initialized");
            }

        } finally {
            INITIALIZE_LOCK.unlock();
        }
    }

    public static ApplicationContext getInstance() {

        if (instance != null && INITIALIZED.get()) {

            return instance;
        } else {

            String message = "Context wasn't initialized";
            LOGGER.error(message);
            throw new IllegalStateException(message);
        }
    }

    private static <T> InvocationHandler createTransactionalInvocationHandler(TransactionManager transactionManager, T service) {
        return (proxy, method, args) -> {

            Method declaredMethod = service.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
            if (method.isAnnotationPresent(Transactional.class)
                    || declaredMethod.isAnnotationPresent(Transactional.class)) {
                transactionManager.begin();
                LOGGER.info("Transaction has began");
                try {
                    Object result = method.invoke(service, args);
                    transactionManager.commit();
                    LOGGER.info("Transaction has been commit");
                    return result;
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                    transactionManager.rollback();
                    throw e;
                }
            } else {
                LOGGER.info("Invoke service method without Transaction manager");
                return method.invoke(service, args);
            }
        };
    }

    static <T> T createProxy(ClassLoader classLoader, InvocationHandler handler, Class<T>... toBeProxied) {
        LOGGER.info(String.format("Create a proxy of %s", toBeProxied));
        return (T) Proxy.newProxyInstance(classLoader, toBeProxied, handler);
    }

    private void init() {

        // ConnectionManager
        connectionPool = ConnectionPoolImpl.getInstance();
        TransactionManager transactionManager = new TransactionManagerImpl(connectionPool);
        ConnectionManager connectionManager = new ConnectionManagerImpl(connectionPool, transactionManager);
        LOGGER.info("ConnectionManager initialized");

        //dao
        DishCategoryDao dishCategoryDao = new DishCategoryDaoImpl(connectionManager);
        DeliveryAddressDao deliveryAddressDao = new DeliveryAddressDaoImpl(connectionManager);
        DishDao dishDao = new DishDaoImpl(connectionManager);
        DishFeedbackDao dishFeedbackDao = new DishFeedbackDaoImpl(connectionManager);
        LoyaltyDao loyaltyDao = new LoyaltyDaoImpl(connectionManager);
        UserOrderDao userOrderDao = new UserOrderDaoImpl(connectionManager);
        OrderFeedbackDao orderFeedbackDao = new OrderFeedbackDaoImpl(connectionManager);
        OrderItemDao orderItemDao = new OrderItemDaoImpl(connectionManager);
        UserRoleDao userRoleDao = new UserRoleDaoImpl(connectionManager);
        UserAccountDao userAccountDao = new UserAccountDaoImpl(connectionManager);
        UserAddressDao userAddressDao = new UserAddressDaoImpl(connectionManager);
        WalletDao walletDao = new WalletDaoImpl(connectionManager);
        LOGGER.info("Dao initialized");

        //service
        DishService dishService = new DishServiceImpl(dishDao, dishCategoryDao);
        OrderItemService orderItemService = new OrderItemServiceImpl(orderItemDao, dishService);
        DeliveryAddressService deliveryAddressService = new DeliveryAddressServiceImpl(deliveryAddressDao, userAddressDao);
        UserOrderService userOrderService = new UserOrderServiceImpl(userOrderDao, deliveryAddressService, orderItemService);
        UserService userService = new UserServiceImpl(userAccountDao, userAddressDao, loyaltyDao, walletDao, userRoleDao);
        DishCategoryService dishCategoryService = new DishCategoryServiceImpl(dishCategoryDao);
        DishFeedbackService dishFeedbackService = new DishFeedbackServiceImpl(dishFeedbackDao);
        WalletService walletService = new WalletServiceImpl(walletDao);
        LOGGER.info("Services initialized");

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

        InvocationHandler deliveryAddressServiceHandler = createTransactionalInvocationHandler
                (transactionManager, deliveryAddressService);
        DeliveryAddressService deliveryAddressProxyService = createProxy
                (getClass().getClassLoader(), deliveryAddressServiceHandler, DeliveryAddressService.class);

        InvocationHandler dishFeedbackServiceHandler = createTransactionalInvocationHandler
                (transactionManager, dishFeedbackService);
        DishFeedbackService dishFeedbackServiceProxyService = createProxy
                (getClass().getClassLoader(), dishFeedbackServiceHandler, DishFeedbackService.class);

        LOGGER.info("Proxy of services initialized");

        //init bean validator
        Map<Class<? extends Annotation>, FieldValidator> validatorMap = new HashMap<>();
        validatorMap.put(MaxLength.class, new MaxLengthFieldValidator());
        validatorMap.put(MinLength.class, new MinLengthFieldValidator());
        validatorMap.put(NotEmpty.class, new NotEmptyFieldValidator());
        validatorMap.put(Email.class, new EmailFieldValidator());
        validatorMap.put(Password.class, new PasswordFieldValidator());
        validatorMap.put(PhoneNumber.class, new PhoneNumberFieldValidator());
        validatorMap.put(MinCost.class, new MinCostFieldValidator());
        validatorMap.put(Digits.class, new DigitsFieldValidator());
        BeanValidator beanValidator = new AnnotationBasedBeanValidator(validatorMap);

        LOGGER.info("Bean validator initialized");

        //commands
        Command deleteDishFromOrderCommand = new OrderItemDeleteCommand(orderItemProxyService);
        Command orderDishListDisplayCommand = new OrderItemListDisplayCommand(userOrderProxyService, orderItemProxyService, dishCategoryProxyService);
        Command orderItemAddCommand = new OrderItemAddCommand(orderItemProxyService, dishProxyService, userOrderProxyService);
        Command loginUserSubmitCommand = new LoginUserSubmitCommand(userProxyService);
        Command registerUserSubmitCommand = new RegisterUserSubmitCommand(userProxyService, beanValidator);
        Command dishMenuDisplayCommand = new DishMenuDisplayCommand(dishProxyService, dishCategoryProxyService);
        Command orderCheckoutDisplayCommand = new OrderCheckoutDisplayCommand(orderItemProxyService, userOrderProxyService);
        Command orderCheckoutSubmitCommand = new OrderCheckoutSubmitCommand(userOrderProxyService, beanValidator, walletService);
        Command createDishFeedbackFormSubmitCommand = new CreateDishFeedbackFormSubmitCommand(dishFeedbackService);
        Command createDishFeedbackFormDisplayCommand = new CreateDishFeedbackFormDisplayCommand(dishProxyService);
        Command createDishFormDisplayCommand = new CreateDishFormDisplayCommand(dishCategoryProxyService);
        Command createDishFormSubmitCommand = new CreateDishFormSubmitCommand(dishProxyService, dishCategoryService, beanValidator);
        Command updateDishFormDisplayCommand = new UpdateDishFormDisplayCommand(dishCategoryProxyService, dishService);
        Command updateDishFormSubmitCommand = new UpdateDishFormSubmitCommand(dishProxyService, dishCategoryProxyService);
        Command deleteDishCommand = new DeleteDishCommand(dishProxyService);
        Command createDishCategoryFormSubmitCommand = new CreateDishCategoryFormSubmitCommand(dishCategoryProxyService);
        Command deleteDishCategoryFormSubmitCommand = new DeleteDishCategoryFormSubmitCommand(dishCategoryProxyService);
        Command deleteDishCategoryFormDisplayCommand = new DeleteDishCategoryFormDisplayCommand(dishCategoryProxyService);
        Command fillUpWalletFormDisplay = new FillUpWalletFormDisplay(walletService);
        Command fillUpWalletFormSubmit = new FillUpWalletFormSubmit(walletService);
        LOGGER.info("Commands initialized");

        //utils
        JspUtil jspUtil = new JspUtil(dishProxyService, dishCategoryProxyService);

        //commandFactory
        CommandFactory commandFactory = new CommandFactoryImpl();

        commandFactory.registerCommand(ORDER_BASKET_SERVLET_SWITCH, (request, response) -> ORDER_BASKET_SERVLET);
        commandFactory.registerCommand(MENU_SERVLET_SWITCH, (request, response) -> MENU_SERVLET);
        commandFactory.registerCommand(REGISTER_SERVLET_SWITCH, (request, response) -> USER_REGISTER_SERVLET);
        commandFactory.registerCommand(LOGIN_SERVLET_SWITCH, (request, response) -> LOGIN_SERVLET);
        commandFactory.registerCommand(ORDER_CHECKOUT_SERVLET_SWITCH, (request, response) -> ORDER_CHECKOUT_SERVLET);
        commandFactory.registerCommand(INDEX, (request, response) -> INDEX_SERVLET);

        commandFactory.registerCommand(DELETE_DISH_FROM_ORDER, deleteDishFromOrderCommand);
        commandFactory.registerCommand(ORDER_ITEM_LIST_DISPLAY, orderDishListDisplayCommand);
        commandFactory.registerCommand(ADD_ITEM_TO_ORDER, orderItemAddCommand);
        commandFactory.registerCommand(CHECKOUT_ORDER_FORM_DISPLAY, orderCheckoutDisplayCommand);
        commandFactory.registerCommand(CHECKOUT_ORDER_FORM_SUBMIT, orderCheckoutSubmitCommand);

        commandFactory.registerCommand(CREATE_DISH_FEEDBACK_FORM_DISPLAY, createDishFeedbackFormDisplayCommand);
        commandFactory.registerCommand(CREATE_DISH_FEEDBACK_FORM_SUBMIT, createDishFeedbackFormSubmitCommand);
        commandFactory.registerCommand(DISH_MENU_DISPLAY, dishMenuDisplayCommand);
        commandFactory.registerCommand(CREATE_DISH_FORM_DISPLAY, createDishFormDisplayCommand);
        commandFactory.registerCommand(CREATE_DISH_FORM_SUBMIT, createDishFormSubmitCommand);
        commandFactory.registerCommand(UPDATE_DISH_FORM_DISPLAY, updateDishFormDisplayCommand);
        commandFactory.registerCommand(UPDATE_DISH_FORM_SUBMIT, updateDishFormSubmitCommand);
        commandFactory.registerCommand(DELETE_DISH_FROM_MENU, deleteDishCommand);

        commandFactory.registerCommand(CREATE_DISH_CATEGORY_FORM_DISPLAY, ((request, response) -> CREATE_CATEGORY_FORM_JSP));
        commandFactory.registerCommand(CREATE_DISH_CATEGORY_FORM_SUBMIT, createDishCategoryFormSubmitCommand);
        commandFactory.registerCommand(DELETE_DISH_CATEGORY_FORM_DISPLAY, deleteDishCategoryFormDisplayCommand);
        commandFactory.registerCommand(DELETE_DISH_CATEGORY_FORM_SUBMIT, deleteDishCategoryFormSubmitCommand);

        commandFactory.registerCommand(FILL_UP_WALLET_FORM_DISPLAY, fillUpWalletFormDisplay);
        commandFactory.registerCommand(FILL_UP_WALLET_FORM_SUBMIT, fillUpWalletFormSubmit);

        commandFactory.registerCommand(REGISTER_USER_SUBMIT, registerUserSubmitCommand);
        commandFactory.registerCommand(LOGIN_USER_SUBMIT, loginUserSubmitCommand);
        commandFactory.registerCommand(LOGOUT, (request, response) -> {
            request.getSession().invalidate();
            SecurityContext.getInstance().logout(request.getSession().getId());
            return CommandReturnValues.LOGOUT_RESULT;
        });

        LOGGER.info("Command factory initialized");

        //bean command provider
        beans.put(CommandFactory.class, commandFactory);
        beans.put(DishService.class, dishProxyService);
        beans.put(OrderItemService.class, orderItemProxyService);
        beans.put(DeliveryAddressService.class, deliveryAddressProxyService);
        beans.put(UserOrderService.class, userOrderProxyService);
        beans.put(UserService.class, userProxyService);
        beans.put(DishCategoryService.class, dishCategoryProxyService);
        beans.put(DishFeedbackService.class, dishFeedbackServiceHandler);

        beans.put(UserAccountDao.class, userAccountDao);
        beans.put(BeanValidator.class, beanValidator);

        beans.put(ConnectionPool.class, connectionPool);
        beans.put(TransactionManager.class, transactionManager);
        beans.put(ConnectionManager.class, connectionManager);

        beans.put(JspUtil.class, jspUtil);

        LOGGER.info("Bean command provider initialized");

    }

    public void destroy() throws SQLException {

        connectionPool.shutdown();
        beans.clear();
        LOGGER.info("Application context destroyed");
    }

    public <T> T getBean(Class<T> clazz) {

        return (T) this.beans.get(clazz);
    }

    public <T> boolean removeBean(T bean) {

        Object removedBean = this.beans.remove(bean);
        return bean.equals(removedBean);
    }

}
