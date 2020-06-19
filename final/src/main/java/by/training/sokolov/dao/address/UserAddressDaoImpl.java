package by.training.sokolov.dao.address;

import by.training.sokolov.dao.GenericDao;
import by.training.sokolov.dao.IdentifiedRowMapper;
import by.training.sokolov.model.UserAddress;
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

    public UserAddressDaoImpl() {
        super(TABLE_NAME, getUserAddressRowMapper());
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
