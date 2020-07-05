package by.training.sokolov.core.context;

import by.training.sokolov.db.ConnectionPool;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AppContext {

    private static final Logger LOGGER = Logger.getLogger(AppContext.class);
    private static final AtomicBoolean INITIALIZED = new AtomicBoolean(false);
    private static final Lock INITIALIZE_LOCK = new ReentrantLock();
    private static final AppContext INSTANCE;
    private final Map<Class<?>, Object> beans = new HashMap<>();
    private ConnectionPool connectionPool;

    private AppContext() {

    }

    public static void initialize() {
        INITIALIZE_LOCK.lock();
        try {
            if (INSTANCE != null && INITIALIZED.get()) {
                throw new IllegalStateException("Context was already initialized");
            } else {
                AppContext context = new AppContext();
                context.init();
                INSTANCE = context;
                INITIALIZED.set(true);
            }

        } finally {
            INITIALIZE_LOCK.unlock();
        }
    }

    public static AppContext getInstance() {
        if (!INITIALIZED.get()) {
            INITIALIZE_LOCK.lock();
            try {
                if (INSTANCE == null) {
                    INSTANCE = new AppContext();
                    INITIALIZED.set(true);
                }
            } finally {
                INITIALIZE_LOCK.unlock();
            }
        }
        return INSTANCE;
    }


}
