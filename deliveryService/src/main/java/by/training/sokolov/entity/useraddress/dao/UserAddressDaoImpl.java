package by.training.sokolov.entity.useraddress.dao;

import by.training.sokolov.core.dao.GenericDao;
import by.training.sokolov.core.dao.IdentifiedRowMapper;
import by.training.sokolov.database.connection.ConnectionManager;
import by.training.sokolov.entity.useraddress.model.UserAddress;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static by.training.sokolov.entity.useraddress.dao.UserAddressTableConstants.*;

public class UserAddressDaoImpl extends GenericDao<UserAddress> implements UserAddressDao {

    public UserAddressDaoImpl(ConnectionManager connectionManager) {
        super(USER_ADDRESS_TABLE_NAME, getUserAddressRowMapper(), connectionManager);
    }

    private static IdentifiedRowMapper<UserAddress> getUserAddressRowMapper() {

        return new IdentifiedRowMapper<UserAddress>() {

            @Override
            public UserAddress map(ResultSet resultSet) throws SQLException {
                UserAddress userAddress = new UserAddress();
                userAddress.setId(resultSet.getLong(ID));
                userAddress.setFullAddress(resultSet.getString(USER_ADDRESS_STRING));
                return userAddress;
            }

            @Override
            public List<String> getColumnNames() {
                return Collections.singletonList(USER_ADDRESS_STRING);
            }

            @Override
            public void populateStatement(PreparedStatement statement, UserAddress entity) throws SQLException {

                statement.setString(1, entity.getFullAddress());
            }
        };
    }
}
