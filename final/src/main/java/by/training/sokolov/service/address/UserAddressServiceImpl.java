package by.training.sokolov.service.address;

import by.training.sokolov.dao.CRUDDao;
import by.training.sokolov.dao.address.UserAddressDao;
import by.training.sokolov.dao.address.UserAddressDaoImpl;
import by.training.sokolov.model.UserAddress;
import by.training.sokolov.service.GenericService;
import by.training.sokolov.service.GenericServiceImpl;

public class UserAddressServiceImpl extends GenericServiceImpl<UserAddress> implements UserAddressService{

    private static GenericService<UserAddress> userAddressService;
    private UserAddressDao userAddressDao;

    private UserAddressServiceImpl(UserAddressDao dao) {
        super(dao);
        this.userAddressDao = dao;
    }

    public static GenericService<UserAddress> getInstance() {
        if (userAddressService == null) {
            UserAddressDao userAddressDao = new UserAddressDaoImpl();
            userAddressService = new UserAddressServiceImpl(userAddressDao);
        }
        return userAddressService;
    }
}
