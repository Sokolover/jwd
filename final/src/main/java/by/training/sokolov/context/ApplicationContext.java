package by.training.sokolov.context;

import by.training.sokolov.command.Command;
import by.training.sokolov.command.CommandFactory;
import by.training.sokolov.command.CommandFactoryImpl;
import by.training.sokolov.command.category.DisplayDishCategoryDeletingFormCommand;
import by.training.sokolov.command.category.SubmitDishCategoryCreatingFormCommand;
import by.training.sokolov.command.category.SubmitDishCategoryDeletingFormCommand;
import by.training.sokolov.command.dish.*;
import by.training.sokolov.command.feedback.DisplayDishFeedbackCreatingFormCommand;
import by.training.sokolov.command.feedback.SubmitDishFeedbackCreatingFormCommand;
import by.training.sokolov.command.order.*;
import by.training.sokolov.command.user.SubmitUserLoginCommand;
import by.training.sokolov.command.user.SubmitUserRegisterCommand;
import by.training.sokolov.command.wallet.DisplayFillingUpWalletFormCommand;
import by.training.sokolov.command.wallet.SubmitFillingUpWalletFormCommand;
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
                LOGGER.info("Transaction has begun");
                try {
                    Object result = method.invoke(service, args);
                    transactionManager.commit();
                    LOGGER.info("Transaction has been committed");
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

    private static <T> T createProxy(ClassLoader classLoader, InvocationHandler handler, Class<T>... toBeProxied) {
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

        InvocationHandler deliveryAddressServiceHandler = createTransactionalInvocationHandler
                (transactionManager, deliveryAddressService);
        DeliveryAddressService deliveryAddressProxyService = createProxy
                (getClass().getClassLoader(), deliveryAddressServiceHandler, DeliveryAddressService.class);

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
        Command deleteOrderItemCommand = new DeleteOrderItemCommand(orderItemProxyService);
        Command displayOrderItemListCommand = new DisplayOrderItemListCommand(userOrderProxyService, orderItemProxyService);
        Command addOrderItemCommand = new AddOrderItemCommand(orderItemProxyService, dishProxyService, userOrderProxyService);
        Command submitUserLoginCommand = new SubmitUserLoginCommand(userProxyService);
        Command submitUserRegisterCommand = new SubmitUserRegisterCommand(userProxyService, beanValidator);
        Command displayDishMenuCommand = new DisplayDishMenuCommand(dishProxyService);
        Command displayOrderCheckoutCommand = new DisplayOrderCheckoutCommand(orderItemProxyService, userOrderProxyService);
        Command submitOrderCheckoutCommand = new SubmitOrderCheckoutCommand(userOrderProxyService, beanValidator, walletService);
        Command submitDishFeedbackCreatingFormCommand = new SubmitDishFeedbackCreatingFormCommand(dishFeedbackService);
        Command displayDishFeedbackCreatingFormCommand = new DisplayDishFeedbackCreatingFormCommand();
        Command displayDishCreatingFormCommand = new DisplayDishCreatingFormCommand();
        Command submitDishCreatingFormCommand = new SubmitDishCreatingFormCommand(dishProxyService, beanValidator);
        Command displayDishUpdatingFormCommand = new DisplayDishUpdatingFormCommand();
        Command submitDishUpdatingFormCommand = new SubmitDishUpdatingFormCommand(dishProxyService, dishCategoryService, beanValidator);
        Command deleteDishCommand = new DeleteDishCommand(dishProxyService);
        Command submitDishCategoryCreatingFormCommand = new SubmitDishCategoryCreatingFormCommand(dishCategoryService, beanValidator);
        Command submitDishCategoryDeletingFormCommand = new SubmitDishCategoryDeletingFormCommand(dishCategoryService);
        Command displayDishCategoryDeletingFormCommand = new DisplayDishCategoryDeletingFormCommand(dishCategoryService);
        Command displayFillingUpWalletFormCommand = new DisplayFillingUpWalletFormCommand();
        Command submitFillingUpWalletFormCommand = new SubmitFillingUpWalletFormCommand(walletService);
        LOGGER.info("Commands initialized");

        //utils
        JspUtil jspUtil = new JspUtil(dishProxyService, dishCategoryService);

        //commandFactory
        CommandFactory commandFactory = new CommandFactoryImpl();

        commandFactory.registerCommand(ORDER_BASKET_SERVLET_SWITCH, (request, response) -> ORDER_BASKET_SERVLET);
        commandFactory.registerCommand(MENU_SERVLET_SWITCH, (request, response) -> MENU_SERVLET);
        commandFactory.registerCommand(REGISTER_SERVLET_SWITCH, (request, response) -> USER_REGISTER_SERVLET);
        commandFactory.registerCommand(LOGIN_SERVLET_SWITCH, (request, response) -> LOGIN_SERVLET);
        commandFactory.registerCommand(ORDER_CHECKOUT_SERVLET_SWITCH, (request, response) -> ORDER_CHECKOUT_SERVLET);
        commandFactory.registerCommand(INDEX, (request, response) -> INDEX_SERVLET);

        commandFactory.registerCommand(DELETE_ORDER_ITEM_COMMAND, deleteOrderItemCommand);
        commandFactory.registerCommand(DISPLAY_ORDER_ITEM_LIST_COMMAND, displayOrderItemListCommand);
        commandFactory.registerCommand(ADD_ORDER_ITEM_COMMAND, addOrderItemCommand);
        commandFactory.registerCommand(DISPLAY_ORDER_CHECKOUT_COMMAND, displayOrderCheckoutCommand);
        commandFactory.registerCommand(SUBMIT_ORDER_CHECKOUT_COMMAND, submitOrderCheckoutCommand);

        commandFactory.registerCommand(DISPLAY_DISH_FEEDBACK_CREATING_FORM_COMMAND, displayDishFeedbackCreatingFormCommand);
        commandFactory.registerCommand(SUBMIT_DISH_FEEDBACK_CREATING_FORM_COMMAND, submitDishFeedbackCreatingFormCommand);
        commandFactory.registerCommand(DISPLAY_DISH_MENU_COMMAND, displayDishMenuCommand);
        commandFactory.registerCommand(DISPLAY_DISH_CREATING_FORM_COMMAND, displayDishCreatingFormCommand);
        commandFactory.registerCommand(SUBMIT_DISH_CREATING_FORM_COMMAND, submitDishCreatingFormCommand);
        commandFactory.registerCommand(DISPLAY_DISH_UPDATING_FORM_COMMAND, displayDishUpdatingFormCommand);
        commandFactory.registerCommand(SUBMIT_DISH_UPDATING_FORM_COMMAND, submitDishUpdatingFormCommand);
        commandFactory.registerCommand(DELETE_DISH_COMMAND, deleteDishCommand);

        commandFactory.registerCommand(DISPLAY_DISH_CATEGORY_CREATING_FORM_COMMAND, ((request, response) -> CREATE_CATEGORY_FORM_JSP));
        commandFactory.registerCommand(SUBMIT_DISH_CATEGORY_CREATING_FORM_COMMAND, submitDishCategoryCreatingFormCommand);
        commandFactory.registerCommand(DISPLAY_DISH_CATEGORY_DELETING_FORM_COMMAND, displayDishCategoryDeletingFormCommand);
        commandFactory.registerCommand(SUBMIT_DISH_CATEGORY_DELETING_FORM_COMMAND, submitDishCategoryDeletingFormCommand);

        commandFactory.registerCommand(DISPLAY_FILLING_UP_WALLET_FORM_COMMAND, displayFillingUpWalletFormCommand);
        commandFactory.registerCommand(SUBMIT_FILLING_UP_WALLET_FORM_COMMAND, submitFillingUpWalletFormCommand);

        commandFactory.registerCommand(SUBMIT_USER_REGISTER_COMMAND, submitUserRegisterCommand);
        commandFactory.registerCommand(SUBMIT_USER_LOGIN_COMMAND, submitUserLoginCommand);
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
        beans.put(DishCategoryService.class, dishCategoryService);
        beans.put(DishFeedbackService.class, dishFeedbackService);

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
}
