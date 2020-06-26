package by.training.sokolov.useraddress.service;

import by.training.sokolov.service.GenericService;
import by.training.sokolov.service.GenericServiceImpl;
import by.training.sokolov.useraddress.dao.UserAddressDao;
import by.training.sokolov.useraddress.model.UserAddress;

public class UserAddressServiceImpl extends GenericServiceImpl<UserAddress> implements UserAddressService {

    private static GenericService<UserAddress> userAddressService;
    private UserAddressDao userAddressDao;

    public UserAddressServiceImpl(UserAddressDao dao) {
        super(dao);
        this.userAddressDao = dao;
    }

}
