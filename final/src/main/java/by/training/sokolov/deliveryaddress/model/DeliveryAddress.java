package by.training.sokolov.deliveryaddress.model;

import by.training.sokolov.customdeliveryaddress.model.CustomDeliveryAddress;
import by.training.sokolov.dao.IdentifiedRow;
import by.training.sokolov.useraddress.model.UserAddress;

public class DeliveryAddress implements IdentifiedRow {

    private Long id;
    private CustomDeliveryAddress customDeliveryAddress;
    private UserAddress userAddress;

    public DeliveryAddress(){
        this.customDeliveryAddress = new CustomDeliveryAddress();
        this.userAddress = new UserAddress();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomDeliveryAddress getCustomDeliveryAddress() {
        return customDeliveryAddress;
    }

    public void setCustomDeliveryAddress(CustomDeliveryAddress customDeliveryAddress) {
        this.customDeliveryAddress = customDeliveryAddress;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }
}
