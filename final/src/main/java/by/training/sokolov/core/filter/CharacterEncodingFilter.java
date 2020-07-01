package by.training.sokolov.core.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebFilter(servletNames = {"IndexServlet"}, filterName = "ce_filter")
public class CharacterEncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //fixme сделать нормальное отображение русского
        String characterEncoding = request.getCharacterEncoding();
        String cp1251 = "windows-1251";
        String utf8 = StandardCharsets.UTF_8.name();
        if (!utf8.equalsIgnoreCase(characterEncoding)) {
//            response.setCharacterEncoding(cp1251);
            response.setContentType("text/html;charset=windows-1251");
            request.setCharacterEncoding(cp1251);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
