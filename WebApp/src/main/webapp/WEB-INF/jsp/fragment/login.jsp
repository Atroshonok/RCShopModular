<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean id="user" class="com.atroshonok.dao.entities.User" scope="request"></jsp:useBean>

<div id="loginForm" >
	<c:if test="${errorLoginPassMessage ne null}">
		<div class="alert alert-danger" role="alert">
			${errorLoginPassMessage}
		</div>
	</c:if>
	<sf:form method="POST" action="${pageContext.request.contextPath}/users/login" modelAttribute="user" >
		<div class="form-group">
    		<label for="login"><s:message code="usergadget.login.login"/></label>
    		<sf:input path="login" id="login" class="form-control" placeholder="Login" />
  		</div>
  		<div class="form-group">
    		<label for="password"><s:message code="usergadget.login.password"/></label>
    		<sf:password path="password" id="password" class="form-control" placeholder="Password" />
 		</div>
 		<button class="btn btn-primary" type="submit">
			<span class="glyphicon glyphicon-log-in" aria-hidden="true"></span> <s:message code="usergadget.enter"/>  
		</button>&nbsp;
		<a href="${pageContext.request.contextPath}/users/registration" ><s:message code="usergadget.registration"/></a>
	</sf:form>
</div>
