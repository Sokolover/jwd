package by.training.sokolov.core.factory;

import by.training.sokolov.category.dao.DishCategoryDao;
import by.training.sokolov.category.dao.DishCategoryDaoImpl;
import by.training.sokolov.category.service.DishCategoryService;
import by.training.sokolov.category.service.DishCategoryServiceImpl;
import by.training.sokolov.dish.dao.DishDao;
import by.training.sokolov.dish.dao.DishDaoImpl;
import by.training.sokolov.dish.service.DishService;
import by.training.sokolov.dish.service.DishServiceImpl;
import by.training.sokolov.loyalty.dao.LoyaltyDao;
import by.training.sokolov.loyalty.dao.LoyaltyDaoImpl;
import by.training.sokolov.loyalty.service.LoyaltyService;
import by.training.sokolov.loyalty.service.LoyaltyServiceImpl;
import by.training.sokolov.role.dao.UserRoleDao;
import by.training.sokolov.role.dao.UserRoleDaoImpl;
import by.training.sokolov.role.service.UserRoleService;
import by.training.sokolov.role.service.UserRoleServiceImpl;
import by.training.sokolov.user.dao.UserDao;
import by.training.sokolov.user.dao.UserDaoImpl;
import by.training.sokolov.user.service.UserService;
import by.training.sokolov.user.service.UserServiceImpl;
import by.training.sokolov.useraddress.dao.UserAddressDao;
import by.training.sokolov.useraddress.dao.UserAddressDaoImpl;
import by.training.sokolov.useraddress.service.UserAddressService;
import by.training.sokolov.useraddress.service.UserAddressServiceImpl;
import by.training.sokolov.wallet.dao.WalletDao;
import by.training.sokolov.wallet.dao.WalletDaoImpl;
import by.training.sokolov.wallet.service.WalletService;
import by.training.sokolov.wallet.service.WalletServiceImpl;

public class BeanFactory {

    public static UserService getUserService() {
        UserDao userDao = new UserDaoImpl();
        return new UserServiceImpl(userDao);
    }

    public static LoyaltyService getLoyaltyService() {
        LoyaltyDao loyaltyDao = new LoyaltyDaoImpl();
        return new LoyaltyServiceImpl(loyaltyDao);
    }

    public static WalletService getWalletService() {
        WalletDao walletDao = new WalletDaoImpl();
        return new WalletServiceImpl(walletDao);
    }

    public static UserAddressService getUserAddressService() {
        UserAddressDao userAddressDao = new UserAddressDaoImpl();
        return new UserAddressServiceImpl(userAddressDao);
    }

    public static UserRoleService getUserRoleService() {
        UserRoleDao userRoleDao = new UserRoleDaoImpl();
        return new UserRoleServiceImpl(userRoleDao);
    }

    public static DishService getDishService() {
        DishDao dishDao = new DishDaoImpl();
        return new DishServiceImpl(dishDao);
    }

    public static DishCategoryService getDishCategoryService() {
        DishCategoryDao dishCategoryDao = new DishCategoryDaoImpl();
        return new DishCategoryServiceImpl(dishCategoryDao);
    }


}
