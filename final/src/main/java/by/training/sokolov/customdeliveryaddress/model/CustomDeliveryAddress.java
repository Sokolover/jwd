package by.training.sokolov.customdeliveryaddress.model;

import by.training.sokolov.dao.IdentifiedRow;

public class CustomDeliveryAddress implements IdentifiedRow {

    private Long id;
    private String addressString;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressString() {
        return addressString;
    }

    public void setAddressString(String addressString) {
        this.addressString = addressString;
    }
}
