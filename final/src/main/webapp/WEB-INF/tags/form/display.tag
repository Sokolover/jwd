<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%-- todo этот тег нужен чтобы получать доступ к полям объекта см. 20:30 на видео CustomTag
        в том же видео 22:50 - группировка ApplicationMessages.properties
        ApplicationMessages_ru.properties--%>

<jsp:directive.attribute name="fieldName" required="true" description="field name to display"/>
<jsp:directive.attribute name="beanName" required="true" description="some object"/>
<c:if test="${not empty fieldName and not empty beanName}">
    <p>
        <c:set var="bean" value="${requestScope[beanName]}"/>
        <label><fmt:message key="${beanName}.${fieldName}"/>:</label>
        <span><c:out value="${bean[fieldName]}"/></span>
    </p>
</c:if>
