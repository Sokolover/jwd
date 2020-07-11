package by.training.sokolov.entity.order.model;

import by.training.sokolov.core.dao.IdentifiedRow;
import by.training.sokolov.entity.deliveryaddress.model.DeliveryAddress;
import by.training.sokolov.entity.order.constants.OrderStatus;
import by.training.sokolov.entity.user.model.User;

import java.time.LocalDateTime;

public class UserOrder implements IdentifiedRow {

    private Long id;
    private LocalDateTime timeOfDelivery;
    private OrderStatus orderStatus;
    private User user;
    private DeliveryAddress deliveryAddress;
    private String customerName;
    private String customerPhoneNumber;

    public UserOrder() {
        this.user = new User();
        this.deliveryAddress = new DeliveryAddress();
    }

    @Override
    public String toString() {
        return "UserOrder{" +
                "id=" + id +
                ", timeOfDelivery=" + timeOfDelivery +
                ", orderStatus=" + orderStatus +
                ", user=" + user +
                ", deliveryAddress=" + deliveryAddress +
                ", customerName='" + customerName + '\'' +
                ", customerPhoneNumber='" + customerPhoneNumber + '\'' +
                '}';
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimeOfDelivery() {
        return timeOfDelivery;
    }

    public void setTimeOfDelivery(LocalDateTime timeOfDelivery) {
        this.timeOfDelivery = timeOfDelivery;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }
}
