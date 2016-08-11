<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="pagelocale"/>

<div id="loginForm" >
	<c:if test="${errorLoginPassMessage ne null}">
		<div class="alert alert-danger" role="alert">
			${errorLoginPassMessage}	
		</div>
	</c:if>
	<form method="POST" action="controller">
		<input type="hidden" name="command" value="login" /> 
		<div class="form-group">
    		<label for="login"><fmt:message key="usergadget.login.login"/></label>
    		<input id="login" type="text" class="form-control" name="login" placeholder="Login">
  		</div>
 		<div class="form-group">
    		<label for="password"><fmt:message key="usergadget.login.password"/></label>
    		<input id="password" type="password" class="form-control" name="password" placeholder="Password">
 		</div>
		<button class="btn btn-primary" type="submit">
			<span class="glyphicon glyphicon-log-in" aria-hidden="true"></span> <fmt:message key="usergadget.enter"/>  
		</button>&nbsp;
		<a href="controller?command=getregistrform" ><fmt:message key="usergadget.registration"/></a>
	</form>
</div>
