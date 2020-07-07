package by.training.sokolov.entity.deliveryaddress.service;

import by.training.sokolov.core.service.GenericServiceImpl;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.deliveryaddress.dao.DeliveryAddressDao;
import by.training.sokolov.entity.deliveryaddress.model.DeliveryAddress;
import by.training.sokolov.entity.useraddress.dao.UserAddressDao;

import java.sql.SQLException;

public class DeliveryAddressServiceImpl extends GenericServiceImpl<DeliveryAddress> implements DeliveryAddressService {

    private final DeliveryAddressDao deliveryAddressDao;
    private final UserAddressDao userAddressDao;

    public DeliveryAddressServiceImpl(DeliveryAddressDao dao, UserAddressDao userAddressDao) {
        super(dao);
        this.deliveryAddressDao = dao;
        this.userAddressDao = userAddressDao;
    }

    @Override
    public DeliveryAddress getById(Long id) throws SQLException, ConnectionException {

        DeliveryAddress deliveryAddress = deliveryAddressDao.getById(id);
        Long userAddressId = deliveryAddress.getUserAddress().getId();
        deliveryAddress.setUserAddress(userAddressDao.getById(userAddressId));

        return deliveryAddress;
    }
}
