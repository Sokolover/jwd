package by.training.sokolov.core.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import static by.training.sokolov.core.constants.CommonAppConstants.COOKIE_NAME_LANG;

@WebFilter(urlPatterns = "/*", filterName = "lang_filter")
public class LangFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(LangFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (request instanceof HttpServletRequest) {
            String lang = request.getParameter(COOKIE_NAME_LANG);
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            Cookie langCookie;
            LangEnum langEnum = LangEnum.fromString(lang);

            if (langEnum != LangEnum.DEFAULT) {
                langCookie = new Cookie(COOKIE_NAME_LANG, langEnum.getValue());
                LOGGER.info("New language cookie created");
            } else {
                Optional<Cookie[]> cookies = Optional.ofNullable(httpRequest.getCookies());
                langCookie = cookies.map(Stream::of)
                        .orElse(Stream.empty())
                        .filter(cookie -> cookie.getName().equalsIgnoreCase(COOKIE_NAME_LANG))
                        .findFirst()
                        .orElse(new Cookie(COOKIE_NAME_LANG, LangEnum.ENGLISH.getValue()));
            }
            langCookie.setPath(httpRequest.getContextPath());
            httpRequest.setAttribute(COOKIE_NAME_LANG, lang);
            ((HttpServletResponse) response).addCookie(langCookie);
            LOGGER.info("Set language to existing lang cookie");
        }
        chain.doFilter(request, response);

        LOGGER.info("Language filter done");
    }

    @Override
    public void destroy() {

    }
}
