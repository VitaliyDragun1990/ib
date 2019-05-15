<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<div class="visible-xs-block visible-sm-block sm-category-container">
 <a data-toggle="collapse" data-target="#categories">Categories <span class="caret"></span></a>
</div>
<div id="categories" class="panel panel-default collapse" data-spy="affix" data-offset-top="60" data-offset-bottom="400">
	<div class="panel-heading hidden-sm hidden-xs">Categories</div>
	<div class="list-group">
		<c:forEach var="entry" items="${CATEGORY_MAP}">
			<c:set scope="page" var="c" value="${entry.value}"/>
			<a href='<c:url value="/news${c.url}" />' class="list-group-item ${selectedCategoryUrl == c.url ? 'active' : ''}">
				<span class="badge">${c.numberOfArticles}</span> ${c.name}
			</a>
		</c:forEach>
	</div>
</div>