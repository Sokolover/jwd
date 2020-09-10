package by.training.sokolov.filter;

import by.training.sokolov.command.CommandType;
import by.training.sokolov.context.SecurityContext;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.COMMAND_RESULT_MESSAGE_JSP;
import static by.training.sokolov.core.constants.JspName.MAIN_LAYOUT_JSP;

@WebFilter(urlPatterns = "/*", filterName = "security")
public class CommandSecurityFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(CommandSecurityFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        SecurityContext securityContext = SecurityContext.getInstance();
        String command = servletRequest.getParameter(QUERY_PARAM_COMMAND);

        Optional<CommandType> commandType = CommandType.of(command);

        if (commandType.isPresent() && securityContext.canExecute(commandType.get(), servletRequest.getSession().getId())) {
            chain.doFilter(request, response);
        } else if (!commandType.isPresent()) {
            chain.doFilter(request, response);
        } else {
            securityContext.setSecurityAttributes((HttpServletRequest) request);
            String message = "Forbidden to execute command";
            request.setAttribute(ERROR_JSP_ATTRIBUTE, message);
            request.setAttribute(VIEW_NAME_JSP_PARAM, COMMAND_RESULT_MESSAGE_JSP);
            LOGGER.info(message);
            request.getRequestDispatcher(MAIN_LAYOUT_JSP).forward(request, response);
        }

        LOGGER.info("Command security filter done");

    }

    @Override
    public void destroy() {

    }
}
