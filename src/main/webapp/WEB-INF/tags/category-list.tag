<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="custom" uri="/WEB-INF/tld/custom.tld"%>

<%@ attribute name="categories" required="true" type="java.util.Collection" rtexprvalue="true" %>
<%@ attribute name="searchQuery" required="false" type="java.lang.String" rtexprvalue="true" %>
<%@ attribute name="selectedCategoryUrl" required="false" type="java.lang.String" rtexprvalue="true" %>

<c:forEach var="c" items="${categories}">
	<c:choose>
		<c:when test="${!empty searchQuery}">
			<c:url var="url" value="/search" >
				<c:param name="categoryUrl" value="${c.url}"/>
				<c:param name="query" value="${searchQuery}"/>
			</c:url>
		</c:when>
		<c:otherwise>
			<c:url var="url" value="/news${c.url}" />
		</c:otherwise>
	</c:choose>
	<a href='${url}' class="list-group-item ${selectedCategoryUrl == c.url ? 'active' : ''}">
		<span class="badge">${c.numberOfArticles}</span>
		${c.name}
	</a>
</c:forEach>