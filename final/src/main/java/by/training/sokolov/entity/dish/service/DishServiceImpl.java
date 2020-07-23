package by.training.sokolov.entity.dish.service;

import by.training.sokolov.core.service.GenericServiceImpl;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.db.Transactional;
import by.training.sokolov.entity.category.dao.DishCategoryDao;
import by.training.sokolov.entity.category.model.DishCategory;
import by.training.sokolov.entity.dish.dao.DishDao;
import by.training.sokolov.entity.dish.model.Dish;

import java.sql.SQLException;
import java.util.List;

public class DishServiceImpl extends GenericServiceImpl<Dish> implements DishService {

    private DishDao dishDao;
    private DishCategoryDao dishCategoryDao;

    public DishServiceImpl(DishDao dishDao, DishCategoryDao dishCategoryDao) {
        super(dishDao);
        this.dishDao = dishDao;
        this.dishCategoryDao = dishCategoryDao;
    }

    @Transactional
    @Override
    public List<Dish> findAll() throws SQLException, ConnectionException {

        List<Dish> dishes = super.findAll();
        for (Dish dish : dishes) {
            Long categoryId = dish.getDishCategory().getId();
            DishCategory dishCategory = dishCategoryDao.getById(categoryId);
            dish.setDishCategory(dishCategory);
        }
        return dishes;
    }

    @Transactional
    @Override
    public Dish getById(Long id) throws SQLException, ConnectionException {

        Dish dish = super.getById(id);
        DishCategory dishCategory = dishCategoryDao.getById(dish.getDishCategory().getId());
        dish.setDishCategory(dishCategory);
        return dish;
    }

    @Override
    public List<Dish> getByCategory(String categoryName) throws ConnectionException, SQLException {

        return dishDao.getByCategory(categoryName);
    }

    @Transactional
    @Override
    public void update(Dish entity) throws SQLException, ConnectionException {

        String categoryName = entity.getDishCategory().getCategoryName();
        DishCategory dishCategory = dishCategoryDao.getByName(categoryName);
        entity.setDishCategory(dishCategory);
        super.update(entity);
    }

    @Override
    public Long save(Dish entity) throws SQLException, ConnectionException {

        String categoryName = entity.getDishCategory().getCategoryName();
        DishCategory dishCategory = dishCategoryDao.getByName(categoryName);
        entity.setDishCategory(dishCategory);
        return super.save(entity);
    }
}
