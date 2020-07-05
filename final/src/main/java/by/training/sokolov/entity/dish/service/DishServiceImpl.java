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


}
