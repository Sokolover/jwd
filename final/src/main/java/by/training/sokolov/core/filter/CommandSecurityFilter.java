package by.training.sokolov.core.filter;

import by.training.sokolov.command.constants.CommandType;
import by.training.sokolov.core.context.SecurityContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.ERROR_MESSAGE_JSP;
import static by.training.sokolov.core.constants.JspName.MAIN_LAYOUT_JSP;

@WebFilter(urlPatterns = "/*", filterName = "security")
public class CommandSecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        SecurityContext securityContext = SecurityContext.getInstance();
        String command = servletRequest.getParameter(QUERY_COMMAND_PARAM);

        Optional<CommandType> commandType = CommandType.of(command);

        if (commandType.isPresent() && securityContext.canExecute(commandType.get(), servletRequest.getSession().getId())) {
            chain.doFilter(request, response);
        } else if (!commandType.isPresent()) {
            chain.doFilter(request, response);
        } else {
//            ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath());
            securityContext.setSecurityAttributes((HttpServletRequest) request);
            request.setAttribute(ERROR_JSP_ATTRIBUTE, "Forbidden to execute command");
            request.setAttribute(VIEW_NAME_JSP_PARAM, ERROR_MESSAGE_JSP);
            request.getRequestDispatcher(MAIN_LAYOUT_JSP).forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
