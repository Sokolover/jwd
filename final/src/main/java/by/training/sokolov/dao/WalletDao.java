package by.training.sokolov.dao;

import by.training.sokolov.model.Wallet;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WalletDao extends GenericDao<Wallet> {

    private final static Logger LOGGER = Logger.getLogger(WalletDao.class.getName());
    private static final String TABLE_NAME = "wallet";
    private final Lock connectionLock = new ReentrantLock();

    public WalletDao() {
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
