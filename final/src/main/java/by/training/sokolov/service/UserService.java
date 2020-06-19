package by.training.sokolov.service;

import by.training.sokolov.dao.CRUDDao;
import by.training.sokolov.dto.user.UserDto;
import by.training.sokolov.model.Loyalty;
import by.training.sokolov.model.UserAddress;
import by.training.sokolov.model.Wallet;

import java.math.BigDecimal;
import java.sql.SQLException;

public class UserService extends GenericServiceImpl<UserDto> {

    public UserService(CRUDDao<UserDto> dao) {

        super(dao);
    }

    @Override
    public Long save(UserDto entity) throws SQLException {

        Wallet wallet = entity.getWallet();
        wallet.setMoneyAmount(new BigDecimal(0));

        GenericService<Wallet> ws = WalletService.getInstance();
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

        GenericService<UserAddress> userAddressService = UserAddressService.getInstance();
        Long addressId = userAddressService.save(userAddress);

        entity.getUserAddress().setId(addressId);

        //todo протестить будет ли теперь сохранятся пользователь

        return super.save(entity);
    }
}
