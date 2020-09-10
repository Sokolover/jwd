package by.training.sokolov.entity.wallet.dao;

import by.training.sokolov.core.dao.GenericDao;
import by.training.sokolov.core.dao.IdentifiedRowMapper;
import by.training.sokolov.database.connection.ConnectionManager;
import by.training.sokolov.entity.wallet.model.Wallet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static by.training.sokolov.entity.wallet.dao.WalletTableConstants.*;

public class WalletDaoImpl extends GenericDao<Wallet> implements WalletDao {

    public WalletDaoImpl(ConnectionManager connectionManager) {
        super(WALLET_TABLE_NAME, getWalletRowMapper(), connectionManager);
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
