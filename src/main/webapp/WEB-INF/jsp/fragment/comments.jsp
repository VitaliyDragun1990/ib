<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="custom" uri="/WEB-INF/tld/custom.tld"%>

<c:set var="noAvatar" value="/static/img/no_avatar.png"/>
<c:forEach var="comment" items="${comments}">
	<c:set var="account" value="${comment.account}"/>
	
	<div class="meida comment-item" data-comment-id="${comment.id}">
		<div class="media-left">
			<img src='<c:url value="${!empty account.avatar ? account.avatar : noAvatar}" />' alt="${account.name}" class="media-object img-rounded">
		</div>
		<div class="media-body">
			<h4 class="media-heading">${account.name}</h4>
			<p>${comment.content}</p>
			<p>
				<small class="date">
					<fmt:parseDate value="${comment.created}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
					<fmt:formatDate dateStyle="MEDIUM" timeStyle="SHORT" type="both" value="${parsedDateTime}" />
				</small> | <a class="reply" data-name="${account.name}"><custom:message key="app.comments.reply"/></a>
			</p>
		</div>
	</div>
</c:forEach>
