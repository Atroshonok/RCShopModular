<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:setBundle basename="pagelocale"/>
<c:set var="activePage" scope="request" value="0"/>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Main</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
	<link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css"/>
	<link rel="stylesheet" type="text/css" href="css/main.css"/>	
</head>
<body>
<div class="container">
	<div class="header">	
		<c:import url="/jsp/fragment/header.jsp" />
	</div>
	<hr/>
	<div class="body">
		<div class="leftPart" >
			<c:import url="/jsp/fragment/usergadget.jsp" /><br/>
			
			<c:import url="/jsp/fragment/navbar.jsp" />
		</div>	
		<div class="rigthPart">
			<c:if test="${mainInfoMessage != null}">
				<div class="alert alert-info" role="alert">
					${mainInfoMessage}	
				</div>
			</c:if>
			<c:if test="${sessionScope.userType eq 'GUEST'}">
				<h1><fmt:message key="productlist.message.welcome"/></h1><br/>
				<h4 class="alert alert-danger"><fmt:message key="productlist.message.registerplease"/></h4><br/>
			</c:if>
		</div>
	</div>
</div>
<c:import url="/jsp/fragment/footer.jsp" />
<script type="text/javascript" src="js/jquery-3.1.0.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
</body>
</html>