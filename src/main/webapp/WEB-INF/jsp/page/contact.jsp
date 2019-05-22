<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="custom" uri="/WEB-INF/tld/custom.tld" %>

<div class="panel panel-default">
	<div class="panel-body text-justify">
		<h2 class="text-center"><custom:message key="app.contact.header"/></h2>
		<hr>

		<div class="row">
			<div class="col-sm-8 col-sm-offset-2 col-md-8 col-md-offset-2 col-lg-6 col-lg-offset-3">
				<form id="contactForm" action='<c:url value="/contact" />' method="post">
					<c:if test="${displayInfoMessage}">
						<div class="alert alert-success">
  							<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> <custom:message key="app.contact.successMessage"/>
						</div>
					</c:if>
					<div class="alert alert-danger hidden">
  						<span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span> <custom:message key="app.contact.failureMessage"/>
					</div>
					<div class="form-group name">
						<input class="form-control" name="name" type="text" placeholder='<custom:message key="app.placeholder.name"/>'>
						<span class="help-block hidden"><custom:message key="app.contact.message.name"/></span>
					</div>
					<div class="form-group email">
						<input class="form-control" name="email" type="email" placeholder='<custom:message key="app.placeholder.email"/>'>
						<span class="help-block hidden"><custom:message key="app.contact.message.email"/></span>
					</div>
					<div class="form-group message">
						<textarea name="message" cols="30" class="form-control" placeholder='<custom:message key="app.placeholder.msg"/>'></textarea>
						<span class="help-block hidden"><custom:message key="app.contact.message.text"/></span>
					</div>
					<div class="form-group">
						<button class="btn btn-default" type="submit"><custom:message key="app.button.submit"/></button>
					</div>
				</form>
			</div>
		</div>

	</div>
</div>