package by.training.sokolov.order.model;

import by.training.sokolov.dao.IdentifiedRow;
import by.training.sokolov.deliveryaddress.model.DeliveryAddress;
import by.training.sokolov.user.model.User;

import java.sql.Timestamp;
import java.util.Date;

public class UserOrder implements IdentifiedRow {

    private Long id;
    private Timestamp timeOfDelivery;
    private String orderStatus;
    private Boolean inProgress;
    private User user;
    private DeliveryAddress deliveryAddress;

    public UserOrder(){
        this.user = new User();
        this.deliveryAddress = new DeliveryAddress();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTimeOfDelivery() {
        return timeOfDelivery;
    }

    public void setTimeOfDelivery(Timestamp timeOfDelivery) {
        this.timeOfDelivery = timeOfDelivery;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Boolean getInProgress() {
        return inProgress;
    }

    public void setInProgress(Boolean inProgress) {
        this.inProgress = inProgress;
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
}
