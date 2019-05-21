<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" uri="/WEB-INF/tld/custom.tld" %>

<div class="alert alert-danger hidden-print" role="alert">
	<h1>Code: ${statusCode}</h1>
	<c:choose>
		<c:when test="${statusCode == 400 }"><custom:message key="message.error.400" args="${errorMessage}"/></c:when>
		<c:when test="${statusCode == 401 }"><custom:message key="message.error.401"/></c:when>
		<c:when test="${statusCode == 403 }"><custom:message key="message.error.403"/></c:when>
		<c:when test="${statusCode == 404 }"><custom:message key="message.error.404" args="${errorMessage}"/></c:when>
		<c:otherwise><custom:message locale="${request.locale}" key="message.error.tryLater"/></c:otherwise>
	</c:choose>
</div>