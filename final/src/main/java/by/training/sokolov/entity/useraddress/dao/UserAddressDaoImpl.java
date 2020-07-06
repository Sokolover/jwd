package by.training.sokolov.entity.useraddress.dao;

import by.training.sokolov.core.dao.GenericDao;
import by.training.sokolov.core.dao.IdentifiedRowMapper;
import by.training.sokolov.db.ConnectionManager;
import by.training.sokolov.entity.useraddress.model.UserAddress;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserAddressDaoImpl extends GenericDao<UserAddress> implements UserAddressDao {

    private final static Logger LOGGER = Logger.getLogger(UserAddressDaoImpl.class.getName());
    private static final String TABLE_NAME = "user_address";
    private final Lock connectionLock = new ReentrantLock();

    private final ConnectionManager connectionManager;

    public UserAddressDaoImpl(ConnectionManager connectionManager) {
        super(TABLE_NAME, getUserAddressRowMapper(), connectionManager);
        this.connectionManager = connectionManager;
    }

    private static IdentifiedRowMapper<UserAddress> getUserAddressRowMapper() {

        return new IdentifiedRowMapper<UserAddress>() {

            @Override
            public UserAddress map(ResultSet resultSet) throws SQLException {
                UserAddress userAddress = new UserAddress();
                userAddress.setId(resultSet.getLong("id"));
                userAddress.setFullAddress(resultSet.getString("user_address_string"));
                return userAddress;
            }

            @Override
            public List<String> getColumnNames() {
                return Collections.singletonList("user_address_string");
            }

            @Override
            public void populateStatement(PreparedStatement statement, UserAddress entity) throws SQLException {

                statement.setString(1, entity.getFullAddress());
            }
        };
    }
}
