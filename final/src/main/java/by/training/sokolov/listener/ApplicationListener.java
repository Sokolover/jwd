package by.training.sokolov.listener;

import by.training.sokolov.context.ApplicationContext;
import by.training.sokolov.context.SecurityContext;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

import static by.training.sokolov.core.constants.CommonAppConstants.SECURITY_CONTEXT_JSP_PARAM;

@WebListener
public class ApplicationListener implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(ApplicationListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        SecurityContext securityContext = SecurityContext.getInstance();
        securityContext.initialize(sce.getServletContext());
        sce.getServletContext().setAttribute(SECURITY_CONTEXT_JSP_PARAM, securityContext);

        ApplicationContext.initialize();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        try {
            ApplicationContext.getInstance().destroy();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
