package by.training.sokolov.service.user;

import by.training.sokolov.dao.user.UserDao;
import by.training.sokolov.dao.user.UserDaoImpl;
import by.training.sokolov.model.User;
import by.training.sokolov.model.Loyalty;
import by.training.sokolov.model.UserAddress;
import by.training.sokolov.model.Wallet;
import by.training.sokolov.service.GenericService;
import by.training.sokolov.service.GenericServiceImpl;
import by.training.sokolov.service.address.UserAddressServiceImpl;
import by.training.sokolov.service.loyalty.LoyaltyService;
import by.training.sokolov.service.loyalty.LoyaltyServiceImpl;
import by.training.sokolov.service.wallet.WalletServiceImpl;

import java.math.BigDecimal;
import java.sql.SQLException;

public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {

    private static UserService userService;
    private UserDao userDao;

    public UserServiceImpl(UserDao dao) {
        super(dao);
        this.userDao = dao;
    }

    public static UserService getInstance() {
        if (userService == null) {
            UserDao userDao = new UserDaoImpl();
            userService = new UserServiceImpl(userDao);
        }
        return userService;
    }

    @Override
    public Long save(User entity) throws SQLException {

        Wallet wallet = entity.getWallet();
        wallet.setMoneyAmount(new BigDecimal(0));

        GenericService<Wallet> ws = WalletServiceImpl.getInstance();
        Long walletId = ws.save(wallet);

        entity.getWallet().setId(walletId);
//----------------------------------------
        Loyalty loyalty = entity.getLoyalty();
        loyalty.setPointsAmount(0);

        LoyaltyService ls = LoyaltyServiceImpl.getInstance();
        Long loyaltyId = ls.save(loyalty);

        entity.getLoyalty().setId(loyaltyId);
//----------------------------------------

        UserAddress userAddress = entity.getUserAddress();

        GenericService<UserAddress> userAddressService = UserAddressServiceImpl.getInstance();
        Long addressId = userAddressService.save(userAddress);

        entity.getUserAddress().setId(addressId);

        //todo протестить будет ли теперь сохранятся пользователь

        return super.save(entity);
    }
}
