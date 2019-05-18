<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="media-left">
	<img class="avatar img-rounded" src='<c:url value="/static/img/no_avatar.png" />' alt="no avatar" class="media-object img-rounded">
	<a class="logout hidden">Logout</a>
</div>
<div class="media-body">
	<div class="form-group">
		<textarea name="comment" id="commentText" rows="5" class="form-control" placeholder="Type a new comment"></textarea>
	</div>
	<div class="btn-send-container">
		<button id="sendComment" class="btn btn-primary pull-right">Send</button>
	</div>
</div>

<div id="signin-form"  class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title text-center">You should login before leaving comments </h4>
      </div>
      <div class="modal-body">
      	<div class="row">
        <div class="g-signin2 col-md-6 col-md-offset-4" data-onsuccess="onSignIn" data-longtitle="false" data-theme="dark"></div>
      	</div>
      </div>
    </div>
  </div>
</div>