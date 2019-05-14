<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Blog layout demo</title>
  <link href='<c:url value="static/css/bootstrap.css" />' rel="stylesheet">
  <link href='<c:url value="static/css/bootstrap-theme.css" />' rel="stylesheet">
  <link href='<c:url value="static/css/font-awesome.css" />' rel="stylesheet">
  <link href='<c:url value="static/css/app.css" />' rel="stylesheet">
</head>

<body>
  <!-- Header Section  -->
  <header>
  	<jsp:include page="fragment/header.jsp"/>
  </header>

  <!-- Main Content Section -->
  <div class="container">
    <nav class="hidden-print">
    	<ol class="breadcrumb">
    		<li><a href='<c:url value="/news" />'>News</a></li>
    		<li class="active">Bibendum</li>
    	</ol>
    </nav>
    <div class="row">
    	<aside class="col-md-3 col-md-push-9 col-lg-2 col-lg-push-10">
    		<jsp:include page="fragment/aside.jsp"/>
    	</aside>
    	<main class="col-md-9 col-md-pull-3 col-lg-10 col-lg-pull-2">
    		<jsp:include page="${currentPage}" />
    	</main>
    </div>
  </div>

  <!-- Footer Section -->
  <footer>
  	<jsp:include page="fragment/footer.jsp"/>
  </footer>

  <!-- JavaScript -->
  <script>var ctx = "${pageContext.request.contextPath}"</script>
  <script src='<c:url value="static/js/jquery-3.3.1.min.js" />'></script>
  <script src='<c:url value="static/js/bootstrap.js" />'></script>
  <script src='<c:url value="static/js/app.js" />'></script>
</body>

</html>