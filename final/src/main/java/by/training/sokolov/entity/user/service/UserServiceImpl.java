package by.training.sokolov.user.service;

import by.training.sokolov.core.factory.BeanFactory;
import by.training.sokolov.loyalty.model.Loyalty;
import by.training.sokolov.loyalty.service.LoyaltyService;
import by.training.sokolov.service.GenericServiceImpl;
import by.training.sokolov.user.dao.UserDao;
import by.training.sokolov.user.model.User;
import by.training.sokolov.useraddress.model.UserAddress;
import by.training.sokolov.useraddress.service.UserAddressService;
import by.training.sokolov.wallet.dao.WalletDao;
import by.training.sokolov.wallet.dao.WalletDaoImpl;
import by.training.sokolov.wallet.model.Wallet;
import by.training.sokolov.wallet.service.WalletService;
import by.training.sokolov.wallet.service.WalletServiceImpl;

import java.math.BigDecimal;
import java.sql.SQLException;

public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {

    private UserDao userDao;

    public UserServiceImpl(UserDao dao) {
        super(dao);
        this.userDao = dao;
    }

    @Override
    public Long save(User entity) throws SQLException {

        saveUserWallet(entity);
        saveUserLoyalty(entity);
        saveUserAddress(entity);

        return super.save(entity);
    }

    private void saveUserAddress(User entity) throws SQLException {
        UserAddress userAddress = entity.getUserAddress();
        UserAddressService userAddressService = BeanFactory.getUserAddressService();
        Long addressId = userAddressService.save(userAddress);
        entity.getUserAddress().setId(addressId);
    }

    private void saveUserLoyalty(User entity) throws SQLException {
        Loyalty loyalty = entity.getLoyalty();
        loyalty.setPointsAmount(0);
        LoyaltyService loyaltyService = BeanFactory.getLoyaltyService();
        Long loyaltyId = loyaltyService.save(loyalty);
        entity.getLoyalty().setId(loyaltyId);
    }

    private void saveUserWallet(User entity) throws SQLException {
        Wallet wallet = entity.getWallet();
        wallet.setMoneyAmount(new BigDecimal(0));
        WalletDao walletDao = new WalletDaoImpl();
        WalletService walletService = new WalletServiceImpl(walletDao);
        Long walletId = walletService.save(wallet);
        entity.getWallet().setId(walletId);
    }
}
