package by.training.sokolov.entity.deliveryaddress.dao;

import by.training.sokolov.core.dao.GenericDao;
import by.training.sokolov.core.dao.IdentifiedRowMapper;
import by.training.sokolov.database.connection.ConnectionManager;
import by.training.sokolov.entity.deliveryaddress.model.DeliveryAddress;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static by.training.sokolov.entity.deliveryaddress.dao.DeliveryAddressTableConstants.*;

public class DeliveryAddressDaoImpl extends GenericDao<DeliveryAddress> implements DeliveryAddressDao {

    public DeliveryAddressDaoImpl(ConnectionManager connectionManager) {
        super(DELIVERY_ADDRESS_TABLE_NAME, getDeliveryAddressRowMapper(), connectionManager);
    }

    private static IdentifiedRowMapper<DeliveryAddress> getDeliveryAddressRowMapper() {

        return new IdentifiedRowMapper<DeliveryAddress>() {

            @Override
            public DeliveryAddress map(ResultSet resultSet) throws SQLException {
                DeliveryAddress deliveryAddress = new DeliveryAddress();
                deliveryAddress.setId(resultSet.getLong(ID));
                deliveryAddress.setCustomDeliveryAddress(resultSet.getString(CUSTOM_ADDRESS_STRING));
                deliveryAddress.getUserAddress().setId(resultSet.getLong(USER_ADDRESS_ID));
                return deliveryAddress;
            }

            @Override
            public List<String> getColumnNames() {
                return Arrays.asList(CUSTOM_ADDRESS_STRING,
                        USER_ADDRESS_ID);
            }

            @Override
            public void populateStatement(PreparedStatement statement, DeliveryAddress entity) throws SQLException {

                statement.setString(1, entity.getCustomDeliveryAddress());
                statement.setLong(2, entity.getUserAddress().getId());
            }
        };
    }

}
