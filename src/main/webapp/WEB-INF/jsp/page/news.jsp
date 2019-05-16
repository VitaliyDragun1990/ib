<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="iblog" tagdir="/WEB-INF/tags" %>

<div id="articleList">
	<jsp:include page="../fragment/article-list.jsp"/>
</div>

<!-- Pagination section -->
<div class="text-center hidden-print">
	<iblog:pagination pagination="${pagination}"/>
</div>