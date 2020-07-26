package by.training.sokolov.entity.deliveryaddress.service;

import by.training.sokolov.core.service.GenericServiceImpl;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.deliveryaddress.dao.DeliveryAddressDao;
import by.training.sokolov.entity.deliveryaddress.model.DeliveryAddress;
import by.training.sokolov.entity.useraddress.dao.UserAddressDao;
import org.apache.log4j.Logger;

import java.sql.SQLException;

import static by.training.sokolov.core.constants.LoggerConstants.CLASS_INVOKED_METHOD_AND_GOT_MESSAGE;
import static by.training.sokolov.core.constants.LoggerConstants.CLASS_INVOKED_METHOD_FOR_ENTITY_ID_MESSAGE;
import static java.lang.String.format;

public class DeliveryAddressServiceImpl extends GenericServiceImpl<DeliveryAddress> implements DeliveryAddressService {

    private static final Logger LOGGER = Logger.getLogger(DeliveryAddressServiceImpl.class.getName());

    private final DeliveryAddressDao deliveryAddressDao;
    private final UserAddressDao userAddressDao;

    public DeliveryAddressServiceImpl(DeliveryAddressDao dao, UserAddressDao userAddressDao) {
        super(dao);
        this.deliveryAddressDao = dao;
        this.userAddressDao = userAddressDao;
    }

    @Override
    public DeliveryAddress getById(Long id) throws SQLException, ConnectionException {

        String nameOfCurrentMethod = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();

        LOGGER.info(format(CLASS_INVOKED_METHOD_FOR_ENTITY_ID_MESSAGE, this.getClass().getSimpleName(), nameOfCurrentMethod, id));

        DeliveryAddress deliveryAddress = deliveryAddressDao.getById(id);
        Long userAddressId = deliveryAddress.getUserAddress().getId();
        deliveryAddress.setUserAddress(userAddressDao.getById(userAddressId));

        LOGGER.info(format(CLASS_INVOKED_METHOD_AND_GOT_MESSAGE, this.getClass().getSimpleName(), nameOfCurrentMethod, deliveryAddress.toString()));

        return deliveryAddress;
    }
}
