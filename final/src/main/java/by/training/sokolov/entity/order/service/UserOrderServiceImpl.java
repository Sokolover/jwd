package by.training.sokolov.entity.order.service;

import by.training.sokolov.core.context.SecurityContext;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.db.Transactional;
import by.training.sokolov.entity.deliveryaddress.dao.DeliveryAddressDao;
import by.training.sokolov.entity.deliveryaddress.model.DeliveryAddress;
import by.training.sokolov.entity.order.constants.OrderStatus;
import by.training.sokolov.entity.order.dao.UserOrderDao;
import by.training.sokolov.entity.order.model.UserOrder;
import by.training.sokolov.entity.orderitem.model.OrderItem;
import by.training.sokolov.entity.orderitem.service.OrderItemService;
import by.training.sokolov.entity.user.model.User;
import by.training.sokolov.core.service.GenericServiceImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class UserOrderServiceImpl extends GenericServiceImpl<UserOrder> implements UserOrderService {

    private final UserOrderDao userOrderDao;
    private final DeliveryAddressDao deliveryAddressDao;
    private final OrderItemService orderItemService;

    public UserOrderServiceImpl(UserOrderDao userOrderDao, DeliveryAddressDao deliveryAddressDao, OrderItemService orderItemService) {
        super(userOrderDao);
        this.userOrderDao = userOrderDao;
        this.deliveryAddressDao = deliveryAddressDao;
        this.orderItemService = orderItemService;
    }

    @Override
    public UserOrder findInProgressUserOrder(Long id) throws SQLException, ConnectionException {

        return userOrderDao.findInProgressUserOrder(id);
    }

    /*
    todo сделать id на таблицы, а не объекты там, где это надо сделать
     */

    @Transactional
    @Override
    public void createNewOrder(User user) throws SQLException, ConnectionException {

        UserOrder userOrder = new UserOrder();
        userOrder.getUser().setId(user.getId());

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        userOrder.setTimeOfDelivery(timestamp);

        userOrder.setInProgress(true);
        userOrder.setOrderStatus(OrderStatus.START_TO_PROCESS);

        DeliveryAddress userDeliveryAddress = new DeliveryAddress();
        userDeliveryAddress.setUserAddress(user.getUserAddress());
        Long deliveryAddressId = deliveryAddressDao.save(userDeliveryAddress);
        userDeliveryAddress.setId(deliveryAddressId);

        userOrder.setDeliveryAddress(userDeliveryAddress);

        userOrderDao.save(userOrder);
    }

    @Override
    public UserOrder getCurrentUserOrder(String id) throws SQLException, ConnectionException {

        User currentUser = SecurityContext.getInstance().getCurrentUser(id);
        return userOrderDao.findInProgressUserOrder(currentUser.getId());
    }

    @Transactional
    @Override
    public BigDecimal getOrderCost(UserOrder order) throws ConnectionException, SQLException {

        /*
        todo сделать поле стоимосоть ордера в таблице юзер_ордер вместо этого поиска!
         */
        List<OrderItem> orderItems = orderItemService.findAllItemsByOrderId(order.getId());
        BigDecimal orderCost = new BigDecimal(0);
        for(OrderItem orderItem:orderItems){
            orderCost.add(orderItem.getItemCost());
        }
        return orderCost;
    }

}
