package by.training.sokolov.model;

import by.training.sokolov.dao.IdentifiedRow;
import by.training.sokolov.model.Loyalty;
import by.training.sokolov.model.UserAddress;
import by.training.sokolov.model.UserRole;
import by.training.sokolov.model.Wallet;

import java.util.List;

public class User implements IdentifiedRow {

    private Long id;
    private String name;
    private String password;
    private String email;
    private boolean isActive;
    private String phoneNumber;

    private List<UserRole> roles;
    private Loyalty loyalty;
    private Wallet wallet;
    private UserAddress userAddress;

    public User() {
        loyalty = new Loyalty();
        wallet = new Wallet();
        userAddress = new UserAddress();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public Loyalty getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(Loyalty loyalty) {
        this.loyalty = loyalty;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", roles=" + roles +
                ", loyalty=" + loyalty +
                ", wallet=" + wallet +
                ", userAddress=" + userAddress +
                '}';
    }
}
