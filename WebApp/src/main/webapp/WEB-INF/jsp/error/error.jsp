<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page errorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Error page</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css"/>	
</head>
<body>
<div class="container">
	<div class="header">	
		<c:import url="/WEB-INF//jsp/fragment/header.jsp" />
	</div>
	<hr/>
	<div class="body">
		<div class="leftPart" >
			<c:import url="/WEB-INF/jsp/fragment/usergadget.jsp" /><br/>
			<c:import url="/WEB-INF/jsp/fragment/navbar.jsp" />
		</div>	
		<div class="rigthPart">
			<p>Request from ${pageContext.errorData.requestURI} is failed</p>
			<br />
 			<p>Servlet name or type: ${pageContext.errorData.servletName}</p>
 			<br />
 			<p>Status code: ${pageContext.errorData.statusCode}</p>
 			<br />
 			<p>Exception: ${pageContext.errorData.throwable}</p>
 			<br />
 			<p>Error: ${error}</p>
		
			<c:if test="${requestScope.infoMessage != null}">
				<div class="alert alert-info" role="alert">
					${requestScope.infoMessage}	
				</div>
			</c:if>
			<c:if test="${sessionScope.userType eq 'GUEST'}">
				<h1><s:message code="message.welcome"/></h1><br/>
				<h4 class="alert alert-danger"><s:message code="message.registerplease"/></h4><br/>
			</c:if>
		</div>
	</div>
</div>
<c:import url="/WEB-INF/jsp/fragment/footer.jsp" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.1.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
</body>
</html>