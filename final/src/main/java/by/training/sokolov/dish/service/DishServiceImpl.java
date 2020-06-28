package by.training.sokolov.dish.service;

import by.training.sokolov.category.model.DishCategory;
import by.training.sokolov.category.service.DishCategoryService;
import by.training.sokolov.core.factory.BeanFactory;
import by.training.sokolov.dish.dao.DishDao;
import by.training.sokolov.dish.model.Dish;
import by.training.sokolov.service.GenericServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class DishServiceImpl extends GenericServiceImpl<Dish> implements DishService {

    private DishDao dishDao;

    public DishServiceImpl(DishDao dao) {
        super(dao);
        this.dishDao = dao;
    }

    @Override
    public List<Dish> findAll() throws SQLException {
        List<Dish> dishes = super.findAll();

        for (Dish dish : dishes) {

            Long idCategory = dish.getDishCategory().getId();

            DishCategoryService dishCategoryService = BeanFactory.getDishCategoryService();
            DishCategory dishCategory = dishCategoryService.getById(idCategory);
            dish.getDishCategory().setCategoryName(dishCategory.getCategoryName());
        }

        return dishes;
    }
}
