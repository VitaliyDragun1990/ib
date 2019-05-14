<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="media-left">
	<img src='<c:url value="/static/img/no_avatar.png" />' alt="no avatar" class="media-object img-rounded">
</div>
<div class="media-body">
	<div class="form-group">
		<textarea name="comment" id="commentText" rows="5" class="form-control" placeholder="Type a new comment"></textarea>
	</div>
	<div class="btn-send-container">
		<button id="sendComment" class="btn btn-primary pull-right">Send</button>
	</div>
</div>