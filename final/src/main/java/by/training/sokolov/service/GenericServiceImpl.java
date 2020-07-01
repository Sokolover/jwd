package by.training.sokolov.service;

import by.training.sokolov.dao.CrudDao;
import by.training.sokolov.dao.IdentifiedRow;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class GenericServiceImpl<T extends IdentifiedRow> implements GenericService<T> {

    private final static Logger LOGGER = Logger.getLogger(GenericServiceImpl.class.getName());

    private CrudDao<T> crudDao;

    public GenericServiceImpl(CrudDao<T> crudDao) {

        this.crudDao = crudDao;
    }

    @Override
    public Long save(T entity) throws SQLException {

        LOGGER.info("save()--" + entity.toString());
        return crudDao.save(entity);
    }

    @Override
    public void update(T entity) throws SQLException {

        LOGGER.info("update()--" + entity.toString());
        crudDao.update(entity);
    }

    public void deleteById(Long id) throws SQLException{

        LOGGER.info("deleteById(Long id)--" + id);
        crudDao.deleteById(id);
    }

    @Override
    public void delete(T entity) throws SQLException {

        LOGGER.info("delete()--" + entity.toString());
        crudDao.delete(entity);
    }

    @Override
    public T getById(Long id) throws SQLException {

        LOGGER.info("getById()--" + id);
        return crudDao.getById(id);
    }

    @Override
    public List<T> findAll() throws SQLException {

        LOGGER.info("findAll()");
        return crudDao.findAll();
    }
}
