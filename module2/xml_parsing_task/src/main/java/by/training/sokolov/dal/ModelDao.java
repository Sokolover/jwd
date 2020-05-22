package by.training.sokolov.dal;

import java.util.List;

public interface ModelDao<MODEL, KEY> {

    List<MODEL> findAll();

    void save(MODEL model);

    void saveAll(List<MODEL> modelList);

    void update(KEY key, MODEL model);

    MODEL get(KEY key);

    void delete(KEY key);

    void deleteAll();
}
