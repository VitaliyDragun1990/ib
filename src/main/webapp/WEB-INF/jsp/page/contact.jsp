<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="panel panel-default">
	<div class="panel-body text-justify">
		<h2 class="text-center">Contact form</h2>
		<hr>

		<div class="row">
			<div class="col-sm-8 col-sm-offset-2 col-md-8 col-md-offset-2 col-lg-6 col-lg-offset-3">
				<form action='<c:url value="/contact" />' method="post">
					<div class="form-group">
						<input class="form-control" name="name" type="text" placeholder="Your name">
					</div>
					<div class="form-group">
						<input class="form-control" type="email" placeholder="Your email">
					</div>
					<div class="form-group">
						<textarea name="message" cols="30" class="form-control" placeholder="Your message"></textarea>
					</div>
					<div class="form-group">
						<button class="btn btn-default" type="submit">Submit</button>
					</div>
				</form>
			</div>
		</div>

	</div>
</div>