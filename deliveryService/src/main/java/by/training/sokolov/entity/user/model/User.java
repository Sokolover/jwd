package by.training.sokolov.entity.user.model;

import by.training.sokolov.core.dao.IdentifiedRow;
import by.training.sokolov.entity.loyalty.model.Loyalty;
import by.training.sokolov.entity.role.model.UserRole;
import by.training.sokolov.entity.useraddress.model.UserAddress;
import by.training.sokolov.entity.wallet.model.Wallet;
import by.training.sokolov.validation.*;

import java.util.List;

import static by.training.sokolov.core.constants.CommonAppConstants.*;

@ValidBean("user")
public class User implements IdentifiedRow {

    private Long id;
    @MinLength(5)
    @MaxLength(30)
    private String name;
    @NotEmpty
    @Password(regex = PASSWORD_PATTERN)
    private String password;
    @NotEmpty
    @Email(regex = EMAIL_PATTERN)
    private String email;
    private boolean isActive;
    @NotEmpty
    @PhoneNumber(regex = PHONE_NUMBER_PATTERN)
    private String phoneNumber;

    private List<UserRole> roles;
    private Loyalty loyalty;
    private Wallet wallet;
    private UserAddress userAddress;

    public User() {
        this.loyalty = new Loyalty();
        this.wallet = new Wallet();
        this.userAddress = new UserAddress();
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
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
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
