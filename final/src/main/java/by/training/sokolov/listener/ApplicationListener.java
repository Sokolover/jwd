package by.training.sokolov.listener;

import by.training.sokolov.core.context.ApplicationContext;
import by.training.sokolov.core.context.SecurityContext;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class ApplicationListener implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(ApplicationListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        SecurityContext securityContext = SecurityContext.getInstance();
        securityContext.initialize(sce.getServletContext());
        sce.getServletContext().setAttribute("securityContext", securityContext);
        LOGGER.info("Security context initialized");

        ApplicationContext.initialize();
        LOGGER.info("Application context initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        try {
            ApplicationContext.getInstance().destroy();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Application context destroyed");
    }
}
