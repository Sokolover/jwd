package by.training.sokolov.listener;

import by.training.sokolov.core.security.SecurityContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        SecurityContext securityContext = SecurityContext.getInstance();
        securityContext.initialize(sce.getServletContext());
        sce.getServletContext().setAttribute("securityContext", securityContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
