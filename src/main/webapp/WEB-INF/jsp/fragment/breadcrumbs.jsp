<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<ol class="breadcrumb">
	<li><a href='<c:url value="/news" />'>News</a></li>
		<li class="active">Bibendum</li>
	<c:if test="${!empty selectedCategoryUrl}">
<%-- 		<li class="active"><a href='<c:url value="/news${selectedCategoryUrl}" />' >${selectedCategoryUrl}</a></li> --%>
	</c:if>
</ol>