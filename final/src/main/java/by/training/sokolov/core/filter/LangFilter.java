package by.training.sokolov.core.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

@WebFilter(urlPatterns = "/*", filterName = "lang_filter")
public class LangFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (request instanceof HttpServletRequest) {
            String lang = request.getParameter("lang");
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            Cookie langCookie;
            LangEnum langEnum = LangEnum.fromString(lang);

            if (langEnum != LangEnum.DEFAULT) {
                langCookie = new Cookie("lang", langEnum.getValue());
            } else {
                Optional<Cookie[]> cookies = Optional.ofNullable(httpRequest.getCookies());
                langCookie = cookies.map(Stream::of)
                        .orElse(Stream.empty())
                        .filter(cookie -> cookie.getName().equalsIgnoreCase("lang"))
                        .findFirst()
                        .orElse(new Cookie("lang", LangEnum.ENGLISH.getValue()));
            }
            langCookie.setPath(httpRequest.getContextPath());
            httpRequest.setAttribute("lang", lang);
            ((HttpServletResponse) response).addCookie(langCookie);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
