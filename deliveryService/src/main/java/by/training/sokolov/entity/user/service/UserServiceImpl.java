package by.training.sokolov.entity.user.service;

import by.training.sokolov.core.service.GenericServiceImpl;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.database.connection.Transactional;
import by.training.sokolov.entity.loyalty.dao.LoyaltyDao;
import by.training.sokolov.entity.loyalty.model.Loyalty;
import by.training.sokolov.entity.role.dao.UserRoleDao;
import by.training.sokolov.entity.user.dao.UserAccountDao;
import by.training.sokolov.entity.user.model.User;
import by.training.sokolov.entity.useraddress.dao.UserAddressDao;
import by.training.sokolov.entity.useraddress.model.UserAddress;
import by.training.sokolov.entity.wallet.dao.WalletDao;
import by.training.sokolov.entity.wallet.model.Wallet;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static by.training.sokolov.entity.role.constants.RoleName.CLIENT;
import static java.lang.String.format;

public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
    private static final String SET_TO_USER_MESSAGE = "[%s] has been set to User";
    private static final String SET_TO_USER_ATTRIBUTE_MESSAGE = "%s = [%d] has been set to Users attribute - [%s]";

    private UserAccountDao userAccountDao;
    private UserAddressDao userAddressDao;
    private LoyaltyDao loyaltyDao;
    private WalletDao walletDao;
    private UserRoleDao userRoleDao;

    public UserServiceImpl(UserAccountDao userAccountDao, UserAddressDao userAddressDao, LoyaltyDao loyaltyDao, WalletDao walletDao, UserRoleDao userRoleDao) {
        super(userAccountDao);
        this.userAccountDao = userAccountDao;
        this.userAddressDao = userAddressDao;
        this.loyaltyDao = loyaltyDao;
        this.walletDao = walletDao;
        this.userRoleDao = userRoleDao;
    }

    @Override
    public User login(String email, String password) throws ConnectionException, SQLException {

        User user = this.getByEmail(email);

        if (!Objects.isNull(user) && user.getPassword().equals(password)) {
            return user;
        }

        LOGGER.error("Wrong email address or password");

        return null;
    }

    @Transactional
    @Override
    public void register(User user) throws ConnectionException, SQLException {

        user.setRoles(Collections.singletonList(userRoleDao.getByName(CLIENT)));
        String roleNameMessage = format("%s %s", CLIENT, "role");
        LOGGER.info(format(SET_TO_USER_MESSAGE, roleNameMessage));

        boolean activityStatus = true;
        user.setActive(activityStatus);
        String activityMessage = format("Active %b", activityStatus);
        LOGGER.info(format(SET_TO_USER_MESSAGE, activityMessage));

        BigDecimal moneyAmount = new BigDecimal(0);
        user.getWallet().setMoneyAmount(moneyAmount);
        String moneyAmountMessage = format(SET_TO_USER_ATTRIBUTE_MESSAGE, "Money amount", moneyAmount.longValue(), Wallet.class.getSimpleName());
        LOGGER.info(format(SET_TO_USER_MESSAGE, moneyAmountMessage));


        int pointsAmount = 0;
        user.getLoyalty().setPointsAmount(pointsAmount);
        String pointsAmountMessage = format(SET_TO_USER_ATTRIBUTE_MESSAGE, "Loyalty points amount", pointsAmount, Loyalty.class.getSimpleName());
        LOGGER.info(format(SET_TO_USER_MESSAGE, pointsAmountMessage));

        this.save(user);
    }

    @Transactional
    @Override
    public List<User> findAll() throws SQLException, ConnectionException {

        List<User> users = super.findAll();
        for (User user : users) {
            getUserAttributes(user);
        }

        return users;
    }

    private void getUserAttributes(User user) throws SQLException, ConnectionException {

        user.setRoles(userRoleDao.getUserRoles(user));

        Long idLoyalty = user.getLoyalty().getId();
        Loyalty loyalty = loyaltyDao.getById(idLoyalty);
        user.setLoyalty(loyalty);
        LOGGER.info(format(SET_TO_USER_MESSAGE, Loyalty.class.getSimpleName()));

        Long idWallet = user.getWallet().getId();
        Wallet wallet = walletDao.getById(idWallet);
        user.setWallet(wallet);
        LOGGER.info(format(SET_TO_USER_MESSAGE, Wallet.class.getSimpleName()));

        Long idAddress = user.getUserAddress().getId();
        UserAddress userAddress = userAddressDao.getById(idAddress);
        user.setUserAddress(userAddress);
        LOGGER.info(format(SET_TO_USER_MESSAGE, UserAddress.class.getSimpleName()));
    }

    @Transactional
    @Override
    public User getByEmail(String email) throws ConnectionException, SQLException {

        User user = userAccountDao.getByEmail(email);
        if (Objects.isNull(user)) {
            return null;
        }
        this.getUserAttributes(user);

        return user;
    }

    @Transactional
    @Override
    public Long save(User entity) throws SQLException, ConnectionException {

        Long id = walletDao.save(entity.getWallet());
        entity.getWallet().setId(id);
        id = loyaltyDao.save(entity.getLoyalty());
        entity.getLoyalty().setId(id);
        id = userAddressDao.save(entity.getUserAddress());
        entity.getUserAddress().setId(id);
        Long userId = super.save(entity);
        entity.setId(userId);
        userRoleDao.setUserRoles(entity);

        LOGGER.info(String.format("[%s] has been saved", entity.toString()));

        return userId;
    }

}
