package by.training.sokolov.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebFilter(servletNames = {"index"}, filterName = "ce_filter")
public class CharacterEncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String characterEncoding = request.getCharacterEncoding();
        String utf8 = StandardCharsets.UTF_8.name();
        if (!utf8.equalsIgnoreCase(characterEncoding)) {
            request.setCharacterEncoding(utf8);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
