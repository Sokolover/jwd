package by.training.sokolov.entity.user.service;

import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.db.Transactional;
import by.training.sokolov.entity.loyalty.dao.LoyaltyDao;
import by.training.sokolov.entity.loyalty.model.Loyalty;
import by.training.sokolov.entity.role.dao.UserRoleDao;
import by.training.sokolov.entity.user.dao.UserDao;
import by.training.sokolov.entity.user.model.User;
import by.training.sokolov.entity.useraddress.dao.UserAddressDao;
import by.training.sokolov.entity.useraddress.model.UserAddress;
import by.training.sokolov.entity.wallet.dao.WalletDao;
import by.training.sokolov.entity.wallet.model.Wallet;
import by.training.sokolov.core.service.GenericServiceImpl;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static by.training.sokolov.entity.role.constants.RoleName.CLIENT;

public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

    private UserDao userDao;
    private UserAddressDao userAddressDao;
    private LoyaltyDao loyaltyDao;
    private WalletDao walletDao;
    private UserRoleDao userRoleDao;

    public UserServiceImpl(UserDao userDao, UserAddressDao userAddressDao, LoyaltyDao loyaltyDao, WalletDao walletDao, UserRoleDao userRoleDao) {
        super(userDao);
        this.userDao = userDao;
        this.userAddressDao = userAddressDao;
        this.loyaltyDao = loyaltyDao;
        this.walletDao = walletDao;
        this.userRoleDao = userRoleDao;
    }

    @Override
    public User login(String name, String password) throws ConnectionException, SQLException {

        User user = userDao.getByName(name);

        if (!Objects.isNull(user) && user.getPassword().equals(password)) {
            LOGGER.info("user " + user.getName() + " logged in");
            return user;
        }

        LOGGER.info("invalid name or password");
        return null;
    }

    @Transactional
    @Override
    public void register(User user) throws ConnectionException, SQLException {

        user.setRoles(Collections.singletonList(userRoleDao.getByName(CLIENT)));
        user.setActive(true);
        user.getWallet().setMoneyAmount(new BigDecimal(0));
        user.getLoyalty().setPointsAmount(0);

        this.save(user);
    }

    @Transactional
    @Override
    public List<User> findAll() throws SQLException, ConnectionException {

        List<User> users = super.findAll();

        for (User user : users) {

            user.setRoles(userRoleDao.getUserRoles(user));

            Long idLoyalty = user.getLoyalty().getId();
            Loyalty loyalty = loyaltyDao.getById(idLoyalty);
            user.setLoyalty(loyalty);

            Long idWallet = user.getWallet().getId();
            Wallet wallet = walletDao.getById(idWallet);
            user.setWallet(wallet);

            Long idAddress = user.getUserAddress().getId();
            UserAddress userAddress = userAddressDao.getById(idAddress);
            user.setUserAddress(userAddress);
        }

        return users;
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

        return userId;
    }

}
