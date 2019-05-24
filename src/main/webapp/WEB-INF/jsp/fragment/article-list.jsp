<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="custom" uri="/WEB-INF/tld/custom.tld"%>

<c:forEach var="article" items="${articles}">
	<c:set var="category" value="${CATEGORY_MAP[article.categoryId]}" />

	<article class="panel panel-default">
		<div class="thumbnail">
			<a href='<c:url value="${article.articleLink}" />'> <img src="${article.logo}" alt="${article.title}">
			</a>
		</div>
		<div class="panel-body">
			<h3>
				<a href='<c:url value="${article.articleLink}" />'><custom:trim limit="40" text="${article.title}" /></a>
			</h3>
			<ul class="nav navbar-nav">
				<li><a href='<c:url value="/news${category.url}" />'><i class="fa fa-folder" aria-hidden="true"></i>
					${category.name}
				</a></li>
				<li><a href="#" class="no-link"><i class="fa fa-comments" aria-hidden="true"></i>
					<custom:message key="app.article.comments"/>&nbsp;<fmt:formatNumber value="${article.numberOfComments}" />
				</a></li>
				<li><a href="#" class="no-link"><i class="fa fa-clock-o" aria-hidden="true"></i>
					<fmt:parseDate value="${article.created}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
					<fmt:formatDate dateStyle="FULL" timeStyle="SHORT" type="both" value="${parsedDateTime}" />
				</a></li>
				<li><a href="#" class="no-link"><i class="fa fa-eye" aria-hidden="true"></i>
					<custom:message key="app.article.hits"/>&nbsp;<fmt:formatNumber value="${article.numberOfViews}" />
				</a></li>
			</ul>

			<div class="desc">${article.description}</div>
		</div>
	</article>
</c:forEach>