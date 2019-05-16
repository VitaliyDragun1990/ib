<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="pagination" required="true" type="com.revenat.iblog.presentation.model.Pagination" rtexprvalue="true" %>

<c:if test="${!empty pagination}">
	<ul class="pagination">
		<li class="${pagination.previous ? '' : 'disabled'}">
			<c:choose>
				<c:when test="${pagination.previous}">
					<a href='<c:url value="${pagination.previousUrl}" />' aria-label="Previous"> <span aria-hidden="true">&laquo;</span></a>
				</c:when>
				<c:otherwise>
					<a aria-label="Previous"> <span aria-hidden="true">&laquo;</span></a>
				</c:otherwise>
			</c:choose>
		</li>
		<c:forEach var="page" items="${pagination.pages}">
			<c:choose>
				<c:when test="${page.current}"><li class="active"><a>${page.caption}</a></li></c:when>
				<c:when test="${page.ellipsis}"><li><a>...</a></li></c:when>
				<c:otherwise><li><a aria-label="${page.caption}" href='<c:url value="${page.url}" />'>${page.caption}</a></li></c:otherwise>
			</c:choose>
		</c:forEach>
		<li class="${pagination.next ? '' : 'disabled'}">
			<c:choose>
				<c:when test="${pagination.next}">
					<a href='<c:url value="${pagination.nextUrl}" />' aria-label="Next"> <span aria-hidden="true">&raquo;</span></a>
				</c:when>
				<c:otherwise>
					<a aria-label="Next"> <span aria-hidden="true">&raquo;</span></a>
				</c:otherwise>
			</c:choose>
		</li>
	</ul>
</c:if>