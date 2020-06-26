package by.training.sokolov.filter;

import by.training.sokolov.SecurityContext;
import by.training.sokolov.command.CommandType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebFilter(servletNames = {"index"}, filterName = "security")
public class CommandSecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        SecurityContext securityContext = SecurityContext.getInstance();
        /*
        fixme не использовать
                private final ThreadLocal<String> currentSessionIdStorage = new ThreadLocal<>();
                и всё что с этим связано
                сессия != поток выполнения
        */
//        securityContext.setCurrentSessionId(servletRequest.getSession().getId());
        String command = servletRequest.getParameter("_command");

        Optional<CommandType> commandType = CommandType.of(command);

        if (commandType.isPresent() && securityContext.canExecute(commandType.get(), servletRequest.getSession().getId())) {
            chain.doFilter(request, response);
        } else if (!commandType.isPresent()) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendRedirect("?_command=" + CommandType.INDEX.name());
        }
    }

    @Override
    public void destroy() {

    }
}
