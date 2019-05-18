<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="custom" uri="/WEB-INF/tld/custom.tld"%>

<ol class="breadcrumb">
	<c:choose>
		<c:when test="${!empty article}">
			<c:set var="category" value="${CATEGORY_MAP[article.categoryId]}"/>
			<li><a href='<c:url value="/news" />'>${fn:toUpperCase('News')}</a></li>
			<li><a href='<c:url value="/news${category.url}" />'>${fn:toUpperCase(category.name)}</a></li>
			<li class="acive"><custom:trim limit="40" text="${fn:toUpperCase(article.title)}" /></li>
		</c:when>
		<c:when test="${!empty selectedCategory}">
			<li><a href='<c:url value="/news" />'>${fn:toUpperCase('News')}</a></li>
			<li class="active">${fn:toUpperCase(selectedCategory.name)}</li>
		</c:when>
		<c:when test="${isNewsPage}">
			<li class="active">${fn:toUpperCase('News')}</li>
		</c:when>
		<c:otherwise>
			<li><a href='<c:url value="/news" />'>${fn:toUpperCase('News')}</a></li>
		</c:otherwise>
	</c:choose>
</ol>