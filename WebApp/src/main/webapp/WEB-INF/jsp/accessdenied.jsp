<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Access Denied</title>
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
			<c:if test="${sessionScope.errorAccessMessage ne null}">
				<div class="alert alert-danger" role="alert">
					${sessionScope.errorAccessMessage}	
				</div>
			</c:if>
		</div>
	</div>
</div>
<c:import url="/WEB-INF/jsp/fragment/footer.jsp" />
</body>
</html>