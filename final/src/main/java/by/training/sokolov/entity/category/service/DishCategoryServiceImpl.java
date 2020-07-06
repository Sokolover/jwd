package by.training.sokolov.entity.category.service;

import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.category.dao.DishCategoryDao;
import by.training.sokolov.entity.category.model.DishCategory;
import by.training.sokolov.core.service.GenericServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class DishCategoryServiceImpl extends GenericServiceImpl<DishCategory> implements DishCategoryService {

    private DishCategoryDao dishCategoryDao;

    public DishCategoryServiceImpl(DishCategoryDao dao) {
        super(dao);
        this.dishCategoryDao = dao;
    }

    /*
     createTransactionalInvocationHandler не может найти метод List<DishCategory> findAll(),
        скорее всего потому, что он не определён именно в этом классе, а в классе GenericServiceImpl<DishCategory>
        надо это выяснить
        @
        походу так и есть,
        надо переопределить все методы GenericServiceImpl в конкретных сервисах!!!
     */

    @Override
    public List<DishCategory> findAll() throws SQLException, ConnectionException {

        return super.findAll();
    }

}
