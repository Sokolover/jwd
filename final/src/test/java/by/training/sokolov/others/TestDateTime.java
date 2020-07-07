package by.training.sokolov.others;

import by.training.sokolov.db.*;
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
import by.training.sokolov.entity.loyalty.dao.LoyaltyDao;
import by.training.sokolov.entity.loyalty.dao.LoyaltyDaoImpl;
import by.training.sokolov.entity.order.dao.UserOrderDao;
import by.training.sokolov.entity.order.dao.UserOrderDaoImpl;
import by.training.sokolov.entity.order.model.UserOrder;
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
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class TestDateTime {

    @Test
    public void testLocalTime() {


//        String date = LocalDate.now().toString();
//        System.out.println(date);

        String minutesHours = "15:00";
        String seconds = ":00";
        String dateString = LocalDate.now().toString();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = formatter.parse(dateString + " " + minutesHours + seconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Timestamp timestamp = new Timestamp(date.getTime());

        System.out.println(timestamp);
        System.out.println(date);
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
//                    LOGGER.error(e.getMessage());
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

    @Test
    public void go() throws ConnectionException, SQLException {

        ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

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
        DeliveryAddressService deliveryAddressService = new DeliveryAddressServiceImpl(deliveryAddressDao, userAddressDao);
        UserOrderService userOrderService = new UserOrderServiceImpl(userOrderDao, deliveryAddressService, orderItemService);
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


        List<UserOrder> all = userOrderService.findAll();
        int i;
    }
}
