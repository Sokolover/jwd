package by.training.sokolov.service.dish;

import by.training.sokolov.dao.dish.DishDao;
import by.training.sokolov.dao.dish.DishDaoImpl;
import by.training.sokolov.model.Dish;
import by.training.sokolov.service.GenericServiceImpl;

public class DishServiceImpl extends GenericServiceImpl<Dish> implements DishService {

    private static DishService dishService;
    private DishDao dishDao;

    private DishServiceImpl(DishDao dao) {
        super(dao);
        this.dishDao = dao;
    }

    public static DishService getInstance() {
        if (dishService == null) {
            DishDao dishDao = new DishDaoImpl();
            dishService = new DishServiceImpl(dishDao);
        }
        return dishService;
    }
}
