package by.training.sokolov.core.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebFilter(urlPatterns = "/*", filterName = "ce_filter")
public class CharacterEncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String characterEncoding = request.getCharacterEncoding();
        String utf8 = StandardCharsets.UTF_8.name();
//        if (!utf8.equalsIgnoreCase(characterEncoding)) {
            request.setCharacterEncoding(utf8);
            response.setCharacterEncoding(utf8);
//        }
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
