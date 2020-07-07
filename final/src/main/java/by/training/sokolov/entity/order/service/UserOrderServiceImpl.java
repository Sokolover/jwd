package by.training.sokolov.entity.order.service;

import by.training.sokolov.core.context.SecurityContext;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.db.Transactional;
import by.training.sokolov.entity.deliveryaddress.model.DeliveryAddress;
import by.training.sokolov.entity.deliveryaddress.service.DeliveryAddressService;
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
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class UserOrderServiceImpl extends GenericServiceImpl<UserOrder> implements UserOrderService {

    private final UserOrderDao userOrderDao;
    private final DeliveryAddressService deliveryAddressService;
    private final OrderItemService orderItemService;

    public UserOrderServiceImpl(UserOrderDao userOrderDao, DeliveryAddressService deliveryAddressService, OrderItemService orderItemService) {
        super(userOrderDao);
        this.userOrderDao = userOrderDao;
        this.deliveryAddressService = deliveryAddressService;
        this.orderItemService = orderItemService;
    }

    /*
    todo сделать id на таблицы, а не объекты там, где это надо сделать
     */

    @Transactional
    @Override
    public void createNewOrder(User user) throws SQLException, ConnectionException {

        UserOrder userOrder = new UserOrder();
        userOrder.getUser().setId(user.getId());

//        Date date = new Date();
//        Timestamp timestamp = new Timestamp(date.getTime());
        LocalDateTime localDateTime = LocalDateTime.now();
        userOrder.setTimeOfDelivery(localDateTime);

        userOrder.setOrderStatus(OrderStatus.BUILD_UP);

        DeliveryAddress userDeliveryAddress = new DeliveryAddress();
        userDeliveryAddress.setUserAddress(user.getUserAddress());
        Long deliveryAddressId = deliveryAddressService.save(userDeliveryAddress);
        userDeliveryAddress.setId(deliveryAddressId);

        userOrder.setDeliveryAddress(userDeliveryAddress);

        userOrderDao.save(userOrder);
    }

    @Override
    public UserOrder getCurrentUserOrder(String id) throws SQLException, ConnectionException {

        User currentUser = SecurityContext.getInstance().getCurrentUser(id);
        UserOrder userOrder = userOrderDao.findBuildingUpUserOrder(currentUser.getId());
        if(Objects.isNull(userOrder)){
            return null;
        }
        DeliveryAddress deliveryAddress = deliveryAddressService.getById(userOrder.getDeliveryAddress().getId());
        userOrder.setDeliveryAddress(deliveryAddress);

        return userOrder;
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
            orderCost = orderCost.add(orderItem.getItemCost());
        }
        return orderCost;
    }

    @Override
    public void update(UserOrder entity) throws SQLException, ConnectionException {

        super.update(entity);
    }
}
