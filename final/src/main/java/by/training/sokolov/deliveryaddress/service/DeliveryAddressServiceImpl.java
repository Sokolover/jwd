package by.training.sokolov.deliveryaddress.service;

import by.training.sokolov.deliveryaddress.dao.DeliveryAddressDao;
import by.training.sokolov.deliveryaddress.model.DeliveryAddress;
import by.training.sokolov.service.GenericServiceImpl;

public class DeliveryAddressServiceImpl extends GenericServiceImpl<DeliveryAddress> implements DeliveryAddressDao {

    private DeliveryAddressDao deliveryAddressDao;

    public DeliveryAddressServiceImpl(DeliveryAddressDao dao) {
        super(dao);
        this.deliveryAddressDao = dao;
    }
}
