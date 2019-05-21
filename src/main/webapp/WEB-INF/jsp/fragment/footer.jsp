<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" uri="/WEB-INF/tld/custom.tld"%>

<div class="container">
	<div class="row">
		<div class="col-md-6 col-lg-5 center-sm">
  			<h2><i class="fa fa-html5" aria-hidden="true"></i><custom:message key="app.footer.mainHeader"/></h2>
  			<nav>
  				<a href='<c:url value="/news" />' class="link"><custom:message key="app.footer.news"/></a>
  				<a id="search-link" href="#" class="link"><custom:message key="app.footer.search"/></a>
  				<a href='<c:url value="/about" />' class="link"><custom:message key="app.footer.about"/></a>
  				<a href='<c:url value="/contact" />' class="link"><custom:message key="app.footer.contacts"/></a>
  			</nav>
  			<p class="copywrite"><custom:message key="app.footer.copywrite"/></p>
  		</div>
  		<div class="col-md-6 col-lg-4">
  			<ul class="contacts">
  				<li><a href="http://devstudy.net"><i class="fa fa-globe" aria-hidden="true"></i> devstudy.net</a></li>
  				<li><a href="https://www.youtube.com/channel/UCI-cdIEY91ideXWs8_ANK6Q"><i class="fa fa-youtube-play" aria-hidden="true"></i> <custom:message key="app.footer.link.youtube"/></a></li>
  				<li><a href="mailto:devstudy.net@gmail.com"><i class="fa fa-envelope" aria-hidden="true"></i> devstudy.net@gmail.com</a></li>
  			</ul>
  		</div>
  		<div class="col-md-12 col-lg-3 center-sm">
  			<h4><custom:message key="app.footer.secondaryHeader"/></h4>
  			<p><custom:message key="app.footer.desc"/> <a href="http://devstudy.net/course/web-ishop" class="link" target="_blank">devstudy.net</a></p>
  			<div class="social">
  				<a href="https://www.facebook.com/profile.php?id=100011236741156" target="_blank"><i class="fa fa-facebook" aria-hidden="true"></i></a>
  				<a href="https://twitter.com/devstudy_net" target="_blank"><i class="fa fa-twitter" aria-hidden="true"></i></a>
  				<a href="https://plus.google.com/u/0/101660882781649220659/posts" target="_blank"><i class="fa fa-google-plus-square" aria-hidden="true"></i></a>
  				<a href="https://github.com/devstudy-net" target="_blank"><i class="fa fa-github" aria-hidden="true"></i></a>
  			</div>
  		</div>
  	</div>
</div>