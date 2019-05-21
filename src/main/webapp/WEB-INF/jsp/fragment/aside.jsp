<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="iblog" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="custom" uri="/WEB-INF/tld/custom.tld"%>
    
<div class="visible-xs-block visible-sm-block sm-category-container">
 <a data-toggle="collapse" data-target="#categories"><custom:message key="app.aside.categories"/> <span class="caret"></span></a>
</div>
<div id="categories" class="panel panel-default collapse" data-spy="affix" data-offset-top="60" data-offset-bottom="400">
	<div class="panel-heading hidden-sm hidden-xs"><custom:message key="app.aside.categories"/></div>
	<div class="list-group">
		<iblog:category-list categories="${CATEGORY_MAP.values()}"
		 					 selectedCategoryUrl="${!empty selectedCategory ? selectedCategory.url : ''}"
		 					 searchQuery="${searchQuery}"/>
	</div>
</div>