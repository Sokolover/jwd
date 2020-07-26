package by.training.sokolov.entity.category.service;

import by.training.sokolov.core.service.GenericServiceImpl;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.category.dao.DishCategoryDao;
import by.training.sokolov.entity.category.model.DishCategory;

import java.sql.SQLException;
import java.util.List;

public class DishCategoryServiceImpl extends GenericServiceImpl<DishCategory> implements DishCategoryService {

    private DishCategoryDao dishCategoryDao;

    public DishCategoryServiceImpl(DishCategoryDao dao) {
        super(dao);
        this.dishCategoryDao = dao;
    }

    @Override
    public List<DishCategory> findAll() throws SQLException, ConnectionException {

        return super.findAll();
    }

    @Override
    public Long save(DishCategory entity) throws SQLException, ConnectionException {

        return super.save(entity);
    }
}
