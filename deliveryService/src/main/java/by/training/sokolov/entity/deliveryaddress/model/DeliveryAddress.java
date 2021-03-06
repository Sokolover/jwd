package by.training.sokolov.entity.deliveryaddress.model;

import by.training.sokolov.core.dao.IdentifiedRow;
import by.training.sokolov.entity.useraddress.model.UserAddress;

public class DeliveryAddress implements IdentifiedRow {

    private Long id;
    private String customDeliveryAddress;
    private UserAddress userAddress;

    public DeliveryAddress() {
        this.userAddress = new UserAddress();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomDeliveryAddress() {
        return customDeliveryAddress;
    }

    public void setCustomDeliveryAddress(String customDeliveryAddress) {
        this.customDeliveryAddress = customDeliveryAddress;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }
}
