//package by.training.sokolov.application;
//
//import by.training.sokolov.dao.CRUDDao;
//import by.training.sokolov.dao.DishDaoParams;
//import by.training.sokolov.dao.GenericDao;
//import by.training.sokolov.dao.UserDaoParams;
//import by.training.sokolov.db.BasicConnectionPool;
//import by.training.sokolov.db.ConnectionPool;
//import by.training.sokolov.dto.user.UserDto;
//import by.training.sokolov.model.Dish;
//import by.training.sokolov.service.GenericService;
//import by.training.sokolov.service.GenericServiceImpl;
//import org.apache.log4j.Logger;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//@WebServlet(name = "main", urlPatterns = "/")
//public class IndexServlet extends HttpServlet {
//
//    private static final Logger LOGGER = Logger.getLogger(IndexServlet.class.getName());
//    private static final long serialVersionUID = 1838949176432063868L;
//
//    private GenericService<Dish> dishService;
//    private ConnectionPool connectionPool;
//
//    @Override
//    public void init() {
//
//        LOGGER.info("servlet init");
//
//        CRUDDao<Dish> dishDao = new GenericDao<>(DishDaoParams.getTableName(), DishDaoParams.getDishRowMapper());
//
//        dishService = new GenericServiceImpl<>(dishDao);
//
//        try {
//            this.connectionPool = BasicConnectionPool.getInstance();
//            LOGGER.info("got connectionPool");
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage());
//        }
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
//
//        List<Dish> dishes = new ArrayList<>();
//        Dish firstDish = null;
//        Dish newDish = new Dish(114L, "soup2", new BigDecimal(12), "yyy", 3L);
//        Long newDishId = 0L;
//
//        try {
//            dishes = dishService.findAll();
//            firstDish = dishService.getById(1L);
//            newDishId = dishService.save(newDish);
////            dishService.delete(newDish);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        request.setAttribute("dishes", dishes);
//        request.setAttribute("first_dish", firstDish);
//        request.setAttribute("new_dish_id", newDishId);
//
//        try {
//            getServletContext().getRequestDispatcher("/jsp/indexPage.jsp").forward(request, response);
//        } catch (ServletException | IOException e) {
//            LOGGER.error(e.getMessage());
//        }
//
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
//        LOGGER.info("doPost method handle");
//
//        doGet(request, response);
//    }
//
//    @Override
//    public void destroy() {
//        try {
//            connectionPool.shutdown();
//            LOGGER.info("Connection pool size after shutdown() = " + connectionPool.getSize());
//        } catch (SQLException e) {
//            LOGGER.error(e.getMessage());
//        }
//    }
//
//}
