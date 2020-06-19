package by.training.sokolov.service;

import by.training.sokolov.dao.CRUDDao;
import by.training.sokolov.dao.UserAddressDaoImpl;
import by.training.sokolov.model.UserAddress;

public class UserAddressService extends GenericServiceImpl<UserAddress> {

    private static GenericService<UserAddress> userAddressService;

    private UserAddressService(CRUDDao<UserAddress> dao) {
        super(dao);
    }

    public static GenericService<UserAddress> getInstance() {
        if (userAddressService == null) {
            CRUDDao<UserAddress> userAddressDao = new UserAddressDaoImpl();
            userAddressService = new UserAddressService(userAddressDao);
        }
        return userAddressService;
    }
}
