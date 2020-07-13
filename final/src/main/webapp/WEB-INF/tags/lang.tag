<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar">
    <div class="navbar-menu">
        <div class="navbar-end">
            <c:forTokens items="en,ru" delims="," var="lang">
                <span class="navbar-item">
                    <a class="button is-light is-inverted" href="?lang=${lang}">
                        <span><fmt:message key="links.lang.${lang}"/></span>
                    </a>
                </span>
            </c:forTokens>
        </div>
    </div>
</nav>



