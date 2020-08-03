package by.training.sokolov.entity.order.model;

import by.training.sokolov.core.dao.IdentifiedRow;
import by.training.sokolov.entity.deliveryaddress.model.DeliveryAddress;
import by.training.sokolov.entity.order.constants.OrderStatus;
import by.training.sokolov.validation.*;

import java.time.LocalDateTime;

import static by.training.sokolov.core.constants.CommonAppConstants.PHONE_NUMBER_PATTERN;

@ValidBean("userOrder")
public class UserOrder implements IdentifiedRow {

    private Long id;
    private LocalDateTime timeOfDelivery;
    private OrderStatus orderStatus;
    private Long userId;
    private DeliveryAddress deliveryAddress;
    @MinLength(5)
    @MaxLength(20)
    private String customerName;
    @NotEmpty
    @PhoneNumber(regex = PHONE_NUMBER_PATTERN)
    private String customerPhoneNumber;

    public UserOrder() {
        this.deliveryAddress = new DeliveryAddress();
    }

    @Override
    public String toString() {
        return "UserOrder{" +
                "id=" + id +
                ", timeOfDelivery=" + timeOfDelivery +
                ", orderStatus=" + orderStatus +
                ", user=" + userId +
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
