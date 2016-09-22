<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Products</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css"/>
</head>
<body>
<div class="container">
	<div class="header">	
		<c:import url="/WEB-INF/jsp/fragment/header.jsp" />
	</div>
	<hr/>
	<div class="body">
		<div class="leftPart" >
			<c:import url="/WEB-INF/jsp/fragment/usergadget.jsp" /><br/>
			<c:import url="/WEB-INF/jsp/fragment/navbar.jsp" />
		</div>	
		<div class="rigthPart">
			<c:import url="/WEB-INF/jsp/fragment/productslist.jsp" />
		</div>
	</div>
</div>
<c:import url="/WEB-INF/jsp/fragment/footer.jsp" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.1.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
</body>
</html>