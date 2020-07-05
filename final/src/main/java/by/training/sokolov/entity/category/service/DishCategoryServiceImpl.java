package by.training.sokolov.category.service;

import by.training.sokolov.category.dao.DishCategoryDao;
import by.training.sokolov.category.model.DishCategory;
import by.training.sokolov.service.GenericServiceImpl;

public class DishCategoryServiceImpl extends GenericServiceImpl<DishCategory> implements DishCategoryService {

    private DishCategoryDao dishCategoryDao;

    public DishCategoryServiceImpl(DishCategoryDao dao) {
        super(dao);
        this.dishCategoryDao = dao;
    }

}
