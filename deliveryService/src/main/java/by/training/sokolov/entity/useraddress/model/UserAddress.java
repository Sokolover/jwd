package by.training.sokolov.entity.useraddress.model;

import by.training.sokolov.core.dao.IdentifiedRow;

public class UserAddress implements IdentifiedRow {

    private Long id;
    private String fullAddress;

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", fullAddress='" + fullAddress + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }
}
