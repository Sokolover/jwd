package by.training.sokolov.entity.order.service;

import by.training.sokolov.context.SecurityContext;
import by.training.sokolov.core.service.GenericServiceImpl;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.database.connection.Transactional;
import by.training.sokolov.entity.deliveryaddress.model.DeliveryAddress;
import by.training.sokolov.entity.deliveryaddress.service.DeliveryAddressService;
import by.training.sokolov.entity.order.dao.UserOrderDao;
import by.training.sokolov.entity.order.model.UserOrder;
import by.training.sokolov.entity.orderitem.model.OrderItem;
import by.training.sokolov.entity.orderitem.service.OrderItemService;
import by.training.sokolov.entity.user.model.User;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static by.training.sokolov.core.constants.LoggerConstants.CLASS_INVOKED_METHOD_MESSAGE;
import static by.training.sokolov.entity.order.constants.OrderStatus.BUILD_UP;
import static java.lang.String.format;
import static java.util.Objects.isNull;

public class UserOrderServiceImpl extends GenericServiceImpl<UserOrder> implements UserOrderService {

    private static final Logger LOGGER = Logger.getLogger(UserOrderServiceImpl.class.getName());
    private static final String SET_TO_USER_ORDER_MESSAGE = "[%s] has been set to User order";

    private final UserOrderDao userOrderDao;
    private final DeliveryAddressService deliveryAddressService;
    private final OrderItemService orderItemService;

    public UserOrderServiceImpl(UserOrderDao userOrderDao, DeliveryAddressService deliveryAddressService, OrderItemService orderItemService) {

        super(userOrderDao);
        this.userOrderDao = userOrderDao;
        this.deliveryAddressService = deliveryAddressService;
        this.orderItemService = orderItemService;
    }

    @Transactional
    @Override
    public void createNewOrder(User user) throws SQLException, ConnectionException {

        UserOrder userOrder = new UserOrder();
        userOrder.setUserId(user.getId());
        LOGGER.info(format(SET_TO_USER_ORDER_MESSAGE, "User id"));

        userOrder.setTimeOfDelivery(LocalDateTime.now());
        LOGGER.info(format(SET_TO_USER_ORDER_MESSAGE, "Current date-time"));

        userOrder.setOrderStatus(BUILD_UP);
        String statusMessage = BUILD_UP + " status";
        LOGGER.info(format(SET_TO_USER_ORDER_MESSAGE, statusMessage));

        DeliveryAddress userDeliveryAddress = new DeliveryAddress();
        userDeliveryAddress.setUserAddress(user.getUserAddress());
        Long deliveryAddressId = deliveryAddressService.save(userDeliveryAddress);
        userDeliveryAddress.setId(deliveryAddressId);
        userOrder.setDeliveryAddress(userDeliveryAddress);
        LOGGER.info(format(SET_TO_USER_ORDER_MESSAGE, "Delivery address"));

        userOrderDao.save(userOrder);
    }

    @Transactional
    @Override
    public UserOrder getCurrentUserOrder(String id) throws SQLException, ConnectionException {

        User currentUser = SecurityContext.getInstance().getCurrentUser(id);
        if (isNull(currentUser)) {
            return null;
        }

        UserOrder userOrder = userOrderDao.findBuildingUpUserOrder(currentUser.getId());
        if (isNull(userOrder)) {
            return null;
        }

        DeliveryAddress deliveryAddress = deliveryAddressService.getById(userOrder.getDeliveryAddress().getId());
        userOrder.setDeliveryAddress(deliveryAddress);
        LOGGER.info(format(SET_TO_USER_ORDER_MESSAGE, "Delivery address"));

        return userOrder;
    }

    @Transactional
    @Override
    public BigDecimal getOrderCost(UserOrder order) throws ConnectionException, SQLException {

        /*
        todo сделать поле стоимость ордера в таблице юзер_ордер вместо этого поиска!
         */
        String nameOfCurrentMethod = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();

        LOGGER.info(format(CLASS_INVOKED_METHOD_MESSAGE, this.getClass().getSimpleName(), nameOfCurrentMethod));

        List<OrderItem> orderItems = orderItemService.findAllItemsByOrderId(order.getId());

        BigDecimal orderCost = new BigDecimal(0);
        for (OrderItem orderItem : orderItems) {
            orderCost = orderCost.add(orderItem.getItemCost());
        }
        LOGGER.info(format("Got order cost = [%d]", orderCost.longValue()));

        return orderCost;
    }

    @Transactional
    @Override
    public void update(UserOrder entity) throws SQLException, ConnectionException {

        super.update(entity);
        deliveryAddressService.update(entity.getDeliveryAddress());
    }

    @Override
    public void deleteById(Long id) throws SQLException, ConnectionException {

        orderItemService.deleteByOrderId(id);
        super.deleteById(id);
    }
}
