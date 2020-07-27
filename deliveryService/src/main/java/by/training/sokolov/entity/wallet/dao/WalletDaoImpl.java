package by.training.sokolov.entity.wallet.dao;

import by.training.sokolov.core.dao.GenericDao;
import by.training.sokolov.core.dao.IdentifiedRowMapper;
import by.training.sokolov.database.connection.ConnectionManager;
import by.training.sokolov.entity.wallet.model.Wallet;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static by.training.sokolov.entity.wallet.dao.WalletTableConstants.*;

public class WalletDaoImpl extends GenericDao<Wallet> implements WalletDao {

    private static final Logger LOGGER = Logger.getLogger(WalletDaoImpl.class.getName());

    private final Lock connectionLock = new ReentrantLock();

    private final ConnectionManager connectionManager;

    public WalletDaoImpl(ConnectionManager connectionManager) {
        super(WALLET_TABLE_NAME, getWalletRowMapper(), connectionManager);
        this.connectionManager = connectionManager;
    }

    private static IdentifiedRowMapper<Wallet> getWalletRowMapper() {

        return new IdentifiedRowMapper<Wallet>() {

            @Override
            public Wallet map(ResultSet resultSet) throws SQLException {
                Wallet wallet = new Wallet();
                wallet.setId(resultSet.getLong(ID));
                wallet.setMoneyAmount(resultSet.getBigDecimal(MONEY_AMOUNT));
                return wallet;
            }

            @Override
            public List<String> getColumnNames() {
                return Collections.singletonList(MONEY_AMOUNT);
            }

            @Override
            public void populateStatement(PreparedStatement statement, Wallet entity) throws SQLException {

                statement.setBigDecimal(1, entity.getMoneyAmount());
            }
        };
    }
}
