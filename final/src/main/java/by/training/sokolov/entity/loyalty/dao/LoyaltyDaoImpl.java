package by.training.sokolov.entity.loyalty.dao;

import by.training.sokolov.core.dao.GenericDao;
import by.training.sokolov.core.dao.IdentifiedRowMapper;
import by.training.sokolov.db.ConnectionManager;
import by.training.sokolov.entity.loyalty.model.Loyalty;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LoyaltyDaoImpl extends GenericDao<Loyalty> implements LoyaltyDao {

    private final static Logger LOGGER = Logger.getLogger(LoyaltyDaoImpl.class.getName());
    private static final String TABLE_NAME = "loyalty_points";
    private final Lock connectionLock = new ReentrantLock();

    private final ConnectionManager connectionManager;

    public LoyaltyDaoImpl(ConnectionManager connectionManager) {
        super(TABLE_NAME, getLoyaltyRowMapper(), connectionManager);
        this.connectionManager = connectionManager;
    }

    private static IdentifiedRowMapper<Loyalty> getLoyaltyRowMapper() {

        return new IdentifiedRowMapper<Loyalty>() {

            @Override
            public Loyalty map(ResultSet resultSet) throws SQLException {
                Loyalty loyalty = new Loyalty();
                loyalty.setId(resultSet.getLong("id"));
                loyalty.setPointsAmount(resultSet.getInt("points_amount"));
                return loyalty;
            }

            @Override
            public List<String> getColumnNames() {
                return Collections.singletonList("points_amount");
            }

            @Override
            public void populateStatement(PreparedStatement statement, Loyalty entity) throws SQLException {

                statement.setInt(1, entity.getPointsAmount());
            }
        };
    }
}
