package by.training.sokolov.wallet.dao;

import by.training.sokolov.dao.GenericDao;
import by.training.sokolov.dao.IdentifiedRowMapper;
import by.training.sokolov.model.Wallet;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WalletDaoImpl extends GenericDao<Wallet> implements WalletDao {

    private final static Logger LOGGER = Logger.getLogger(WalletDaoImpl.class.getName());
    private static final String TABLE_NAME = "wallet";
    private final Lock connectionLock = new ReentrantLock();

    public WalletDaoImpl() {
        super(TABLE_NAME, getWalletRowMapper());
    }

    private static IdentifiedRowMapper<Wallet> getWalletRowMapper() {

        return new IdentifiedRowMapper<Wallet>() {

            @Override
            public Wallet map(ResultSet resultSet) throws SQLException {
                Wallet wallet = new Wallet();
                wallet.setId(resultSet.getLong("id"));
                wallet.setMoneyAmount(resultSet.getBigDecimal("money_amount"));
                return wallet;
            }

            @Override
            public List<String> getColumnNames() {
                return Collections.singletonList("money_amount");
            }

            @Override
            public void populateStatement(PreparedStatement statement, Wallet entity) throws SQLException {

                statement.setBigDecimal(1, entity.getMoneyAmount());
            }
        };
    }
}
