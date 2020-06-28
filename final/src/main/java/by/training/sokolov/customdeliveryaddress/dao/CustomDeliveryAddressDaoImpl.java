package by.training.sokolov.customdeliveryaddress.dao;

import by.training.sokolov.customdeliveryaddress.model.CustomDeliveryAddress;
import by.training.sokolov.dao.GenericDao;
import by.training.sokolov.dao.IdentifiedRowMapper;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CustomDeliveryAddressDaoImpl extends GenericDao<CustomDeliveryAddress> implements CustomDeliveryAddressDao {

    private final static Logger LOGGER = Logger.getLogger(CustomDeliveryAddressDaoImpl.class.getName());
    private static final String TABLE_NAME = "delivery_address";
    private final Lock connectionLock = new ReentrantLock();

    public CustomDeliveryAddressDaoImpl() {
        super(TABLE_NAME, getCustomDeliveryAddressRowMapper());
    }

    private static IdentifiedRowMapper<CustomDeliveryAddress> getCustomDeliveryAddressRowMapper() {

        return new IdentifiedRowMapper<CustomDeliveryAddress>() {

            @Override
            public CustomDeliveryAddress map(ResultSet resultSet) throws SQLException {
                CustomDeliveryAddress customDeliveryAddress = new CustomDeliveryAddress();
                customDeliveryAddress.setId(resultSet.getLong("id"));
                customDeliveryAddress.setAddressString(resultSet.getString("custom_address_string"));
                return customDeliveryAddress;
            }

            @Override
            public List<String> getColumnNames() {
                return Collections.singletonList("custom_address_string");
            }

            @Override
            public void populateStatement(PreparedStatement statement, CustomDeliveryAddress entity) throws SQLException {

                statement.setString(1, entity.getAddressString());
            }
        };
    }
}
