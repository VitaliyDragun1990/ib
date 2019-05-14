<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<div class="visible-xs-block visible-sm-block sm-category-container">
 <a data-toggle="collapse" data-target="#categories">Categories <span class="caret"></span></a>
</div>
<div id="categories" class="panel panel-default collapse" data-spy="affix" data-offset-top="60" data-offset-bottom="400">
	<div class="panel-heading hidden-sm hidden-xs">Categories</div>
	<div class="list-group">
		<a href='<c:url value="/news"  />' class="list-group-item"> <span class="badge">78</span> Vehicula</a>
		<a href='<c:url value="/news"  />' class="list-group-item"> <span class="badge">75</span> Ornare</a>
    	<a href='<c:url value="/news"  />' class="list-group-item"> <span class="badge">110</span> Blandit</a>
    	<a href='<c:url value="/news"  />' class="list-group-item"> <span class="badge">113</span> Lacinia</a>
    	<a href='<c:url value="/news"  />' class="list-group-item"> <span class="badge">216</span> Dictumst</a>
    	<a href='<c:url value="/news"  />' class="list-group-item"> <span class="badge">95</span> Tempus</a>
    	<a href='<c:url value="/news"  />' class="list-group-item"> <span class="badge">211</span> Feugiat</a>
	</div>
</div>