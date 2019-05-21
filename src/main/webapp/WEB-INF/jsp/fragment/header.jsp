<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" uri="/WEB-INF/tld/custom.tld"%>

<nav class="navbar navbar-default">
	<div class="container-fluid">
  		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#blogNavBar" aria-expanded="false">
				<span class="sr-only">Toggle navigatin</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
  			<h1><a href='<c:url value="/news" />' class="navbar-brand"><custom:message key="app.header.header"/></a></h1>
  		</div>
  		<!-- Collect the nav links, forms and other content for toggling -->
  		<div class="collapse navbar-collapse" id="blogNavBar">
  			<form action='<c:url value="/search" />' method="get" class="navbar-form navbar-right" role="search">
  				<c:if test="${!empty selectedCategory}">
  					<input type="hidden" name="categoryUrl" value="${selectedCategory.url}" />
  				</c:if>
  				<div class="form-group">
  					<input id="search" name="query" type="text" class="form-control" value="${searchQuery}" placeholder='<custom:message key="app.placeholder.query"/>'>
  				</div>
  				<button type="submit" class="btn btn-default"><custom:message key="app.button.find"/></button>
  			</form>
  		</div>
	</div>
</nav>