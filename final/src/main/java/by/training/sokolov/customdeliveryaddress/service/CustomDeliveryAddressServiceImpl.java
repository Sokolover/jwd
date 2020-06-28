package by.training.sokolov.customdeliveryaddress.service;

import by.training.sokolov.customdeliveryaddress.dao.CustomDeliveryAddressDao;
import by.training.sokolov.customdeliveryaddress.model.CustomDeliveryAddress;
import by.training.sokolov.service.GenericServiceImpl;

public class CustomDeliveryAddressServiceImpl extends GenericServiceImpl<CustomDeliveryAddress> implements CustomDeliveryAddressService {

    private CustomDeliveryAddressDao deliveryAddressDao;

    public CustomDeliveryAddressServiceImpl(CustomDeliveryAddressDao dao) {
        super(dao);
        this.deliveryAddressDao = dao;
    }
}
