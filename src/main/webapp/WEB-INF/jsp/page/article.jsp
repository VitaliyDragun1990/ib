<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<article class="panel panel-default">
	<c:set var="category" value="${CATEGORY_MAP[article.categoryId]}" />
	<c:set var="articleLink" value="/article/${article.id}${article.url}" />
	
	<div class="thumbnail">
		<a href='<c:url value="${articleLink}" />'><img src="${article.logo}" alt="${article.title}"></a>
	</div>
	<div class="panel-body">
		<h3>
			<a href='<c:url value="${articleLink}" />'>${article.title}</a>
		</h3>
		<ul class="nav navbar-nav">
			<li><a href='<c:url value="/news${category.url}" />'>
				<i class="fa fa-folder" aria-hidden="true"></i> ${category.name}
			</a></li>
			<li><a href="#" class="no-link">
				<i class="fa fa-comments" aria-hidden="true"></i><fmt:formatNumber value="${article.numberOfComments}"/> comments
			</a></li>
			<li><a href="#" class="no-link">
				<i class="fa fa-clock-o" aria-hidden="true"></i>
				<fmt:parseDate value="${article.created}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
				<fmt:formatDate dateStyle="FULL" timeStyle="SHORT" type="both" value="${parsedDateTime}" />
			</a></li>
			<li><a href="#" class="no-link">
				<i class="fa fa-eye" aria-hidden="true"></i> Hits: <fmt:formatNumber value="${article.numberOfViews}" />
			</a></li>
		</ul>

		<div class="desc">${article.content}</div>
		
		<!-- Social networkd links -->
		<div class="social hidden-print">
			<a href="#"><i class="fa fa-facebook" aria-hidden="true"></i></a>
			<a href="#"><i class="fa fa-twitter" aria-hidden="true"></i></a>
			<a href="#"><i class="fa fa-google-plus-square" aria-hidden="true"></i></a>
			<a href="#"><i class="fa fa-pinterest-p" aria-hidden="true"></i></a>
			<a href="#"><i class="fa fa-linkedin" aria-hidden="true"></i></a>
			<a href="#"><i class="fa fa-vk" aria-hidden="true"></i></a>
			<a href="#"><i class="fa fa-reddit" aria-hidden="true"></i></a>
			<a href="#"><i class="fa fa-plus" aria-hidden="true"></i></a>
		</div>
		<hr>
		
		<!-- Add a comment section  -->
		<div class="new-comment media hidden-print">
			<jsp:include page="../fragment/new-comment.jsp" />
		</div>
		
		<!-- User comments section  -->
		<div class="comments">
			<div id="comments-container" data-comments-count="${article.numberOfComments}" data-article-id="${article.id}">
				<jsp:include page="../fragment/comments.jsp"/>		
			</div>
			
			<div class="hidden-print">
				<!-- Load more comments button  -->
				<a id="loadMore" class="btn btn-info btn-block ${article.numberOfComments > fn:length(comments) ? '' : 'hidden'}">
					Load more
				</a>
				<img src='<c:url value="/static/img/loading.gif" />' alt="Loading" id="loadIndicator" class="hidden center-block">
			</div>
		</div>
		
	</div>
</article>