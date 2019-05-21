<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="custom" uri="/WEB-INF/tld/custom.tld" %>

<div class="alert alert-info alert-dismissible" role="alert">
	<button type="button" class="close" data-dismiss="alert" aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<p><custom:message key="app.search.message" args="${articleCount}"/></p>
</div>
<jsp:include page="news.jsp" />