<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="articleList">
	<jsp:include page="../fragment/article-list.jsp"/>
</div>

<!-- Pagination section -->
<div class="text-center hidden-print">
	<ul class="pagination">
		<li class="disabled">
			<a href="#" aria-label="Previous"> <span aria-hidden="true">&laquo;</span></a>
		</li>
		<li class="active"><a href="#">1</a></li>
		<li><a href="#">2</a></li>
		<li><a href="#">3</a></li>
		<li><a href="#">4</a></li>
		<li><a href="#">5</a></li>
		<li>
			<a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span></a>
		</li>
	</ul>
</div>