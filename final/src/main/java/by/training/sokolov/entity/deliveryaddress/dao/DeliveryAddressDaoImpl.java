package by.training.sokolov.entity.deliveryaddress.dao;

import by.training.sokolov.core.dao.GenericDao;
import by.training.sokolov.core.dao.IdentifiedRowMapper;
import by.training.sokolov.db.ConnectionManager;
import by.training.sokolov.entity.deliveryaddress.model.DeliveryAddress;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class DeliveryAddressDaoImpl extends GenericDao<DeliveryAddress> implements DeliveryAddressDao {

    private static final Logger LOGGER = Logger.getLogger(DeliveryAddressDaoImpl.class.getName());

    private static final String TABLE_NAME = "delivery_address";

    private final ConnectionManager connectionManager;

    public DeliveryAddressDaoImpl(ConnectionManager connectionManager) {
        super(TABLE_NAME, getDeliveryAddressRowMapper(), connectionManager);
        this.connectionManager = connectionManager;
    }

    private static IdentifiedRowMapper<DeliveryAddress> getDeliveryAddressRowMapper() {

        return new IdentifiedRowMapper<DeliveryAddress>() {

            @Override
            public DeliveryAddress map(ResultSet resultSet) throws SQLException {
                DeliveryAddress deliveryAddress = new DeliveryAddress();
                deliveryAddress.setId(resultSet.getLong("id"));
                deliveryAddress.setCustomDeliveryAddress(resultSet.getString("custom_address_string"));
                deliveryAddress.getUserAddress().setId(resultSet.getLong("user_address_id"));
                return deliveryAddress;
            }

            @Override
            public List<String> getColumnNames() {
                return Arrays.asList("custom_address_string",
                        "user_address_id");
            }

            @Override
            public void populateStatement(PreparedStatement statement, DeliveryAddress entity) throws SQLException {

                statement.setString(1, entity.getCustomDeliveryAddress());
                statement.setLong(2, entity.getUserAddress().getId());
            }
        };
    }

}
