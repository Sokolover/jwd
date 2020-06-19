package by.training.sokolov.jdbc;

import by.training.sokolov.db.BasicConnectionPool;
import by.training.sokolov.db.ConnectionPool;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RunWith(JUnit4.class)
public class ConnectionPoolTest {

    private static final Logger LOGGER = Logger.getLogger(ConnectionPoolTest.class);
    private static final int N_THREADS = 20;
    private static final int THREAD_SLEEP = 1_000;
    private static int POOL_CAPACITY;
    private ConnectionPool connectionPool;

    @Before
    public void initializeCP() {
        connectionPool = BasicConnectionPool.getInstance();
        POOL_CAPACITY = connectionPool.getSize();
    }

    @Test
    public void shouldGetConnection() throws InterruptedException, SQLException {

//        ConnectionPool connectionPool = Mockito.spy(BasicConnectionPool.getInstance());
        LOGGER.info("UsedConnections: " + connectionPool.getUsedConnectionsAmount());
        LOGGER.info("AvailableConnections: " + connectionPool.getAvailableConnectionsAmount());
        LOGGER.info("Size: " + connectionPool.getSize());
        ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);
        Set<Integer> hashCodes = Collections.synchronizedSet(new HashSet<>());

        for (int i = 0; i < N_THREADS; i++) {
            LOGGER.info("UsedConnections: " + connectionPool.getUsedConnectionsAmount());
            LOGGER.info("AvailableConnections: " + connectionPool.getAvailableConnectionsAmount());
            //        IntStream.range(0, N_THREADS).forEach(i -> );
            executorService.submit(() -> {
                sleep();
                LOGGER.info("Try to get connection");
                try (Connection connection = connectionPool.getConnection()) {
                    LOGGER.info("working with connection...");
                    sleep();
                    Assert.assertTrue(connection instanceof Proxy);
                    int hashCode = connection.hashCode();
                    hashCodes.add(hashCode);
                    LOGGER.info("release connection: " + hashCode);
                } catch (SQLException | IllegalStateException e) {
                    LOGGER.error(e);
                }
            });
            LOGGER.info("UsedConnections: " + connectionPool.getUsedConnectionsAmount());
            LOGGER.info("AvailableConnections: " + connectionPool.getAvailableConnectionsAmount());
        }

        executorService.awaitTermination(N_THREADS / POOL_CAPACITY, TimeUnit.SECONDS);
        Assert.assertEquals(POOL_CAPACITY, hashCodes.size());
        connectionPool.shutdown();
//        Mockito.verify(((BasicConnectionPool) connectionPool), Mockito.times(N_THREADS)).releaseConnection(Mockito.any());
    }

    private void sleep() {
        try {
            Thread.sleep(Math.abs(new Random().nextInt(THREAD_SLEEP)));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
