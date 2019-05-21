<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="panel panel-default">
	<div class="panel-body text-justify">
		<h2 class="text-center">Contact form</h2>
		<hr>

		<div class="row">
			<div class="col-sm-8 col-sm-offset-2 col-md-8 col-md-offset-2 col-lg-6 col-lg-offset-3">
				<form id="contactForm" action='<c:url value="/contact" />' method="post">
					<c:if test="${success}">
						<div class="alert alert-success">
  							<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> Your request has been sent. Thank your for your message.
						</div>
					</c:if>
					<div class="alert alert-danger hidden">
  						<span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span> There are some errors in your form.
					</div>
					<div class="form-group name">
						<input class="form-control" name="name" type="text" placeholder="Your name">
						<span class="help-block hidden">Name is required.</span>
					</div>
					<div class="form-group email">
						<input class="form-control" name="email" type="email" placeholder="Your email">
						<span class="help-block hidden">Email is invalid.</span>
					</div>
					<div class="form-group message">
						<textarea name="message" cols="30" class="form-control" placeholder="Your message"></textarea>
						<span class="help-block hidden">Message is required.</span>
					</div>
					<div class="form-group">
						<button class="btn btn-default" type="submit">Submit</button>
					</div>
				</form>
			</div>
		</div>

	</div>
</div>