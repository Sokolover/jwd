package by.training.sokolov.dal;

import by.training.sokolov.model.Gem;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class GemDaoImpl implements GemDao {

    private final static Logger LOGGER = Logger.getLogger(GemDaoImpl.class.getName());

    private final Map<Long, Gem> gemStorage = new ConcurrentHashMap<>();

    private AtomicLong sequence = new AtomicLong(0);

    @Override
    public List<Gem> findAll() {

        if (this.gemStorage.isEmpty()) {
            return new ArrayList<>();
        } else {
            return new ArrayList<>(gemStorage.values());
        }
    }

    @Override
    public void save(Gem gem) {

        Gem value = cloneModel(gem);
        long key = sequence.incrementAndGet();
        value.setId(key);
        this.gemStorage.put(key, value);
    }

    @Override
    public void saveAll(List<Gem> gems) {

        for (Gem gem : gems) {
            Gem value = cloneModel(gem);
            long key = sequence.incrementAndGet();
            value.setId(key);
            this.gemStorage.put(key, value);
        }
    }

    @Override
    public void update(Long key, Gem gem) {

        if (this.gemStorage.containsKey(key)) {
            this.gemStorage.put(key, cloneModel(gem));
        } else {
            throw new ModelNotFoundException("key don't exist: " + key);
        }
    }

    @Override
    public Gem get(Long key) {

        if (this.gemStorage.containsKey(key)) {
            return this.gemStorage.get(key);
        } else {
            throw new ModelNotFoundException("key don't exist: " + key);
        }
    }

    @Override
    public void delete(Long key) {

        this.gemStorage.remove(key);
    }

    @Override
    public void deleteAll() {

        LOGGER.info("delete all records and set sequence counter to 0");
        sequence = new AtomicLong(0);
        this.gemStorage.clear();
    }

    private Gem cloneModel(Gem gem) {

        try {
            return gem.clone();
        } catch (CloneNotSupportedException e) {
            LOGGER.error("cannot clone gem objects");
            return new Gem();
        }
    }
}
