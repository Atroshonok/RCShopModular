<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="userGadget">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<c:if test="${sessionScope.userType eq 'GUEST'}">
				<b><s:message code="usergadget.hello"/>, <s:message code="usergadget.guest"/>!"</b>
			</c:if>
			<c:if test="${(sessionScope.userType eq 'ADMIN') or (sessionScope.userType eq 'CLIENT')}">
				<b><span class="glyphicon glyphicon-user" aria-hidden="true"></span><s:message code="usergadget.hello" />, ${sessionScope.user.login}!</b>
			</c:if>
	  	</div>
	  	<div class="panel-body">
	  		<c:if test="${sessionScope.userType eq 'GUEST'}">
				<c:import url="/WEB-INF/jsp/fragment/login.jsp" />
			</c:if>
		  	<c:if test="${sessionScope.userType eq 'CLIENT'}">
				<a href="${pageContext.request.contextPath}/orders/all/${sessionScope.user.id}"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span> <s:message code="usergadget.all.orders"/></a><br/>
				<s:message code="usergadget.session.orders"/>: <span class="badge">${sessionScope.cart.getOrders().size()}</span><br/>
				<a href="${pageContext.request.contextPath}/cart"><span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span> <s:message code="usergadget.cart"/></a><br/>
				<s:message code="usergadget.cart.products"/>: <span class="badge">${sessionScope.cart.getAllProductsCount()}</span><br/>
			</c:if>
			<c:if test="${(sessionScope.userType eq 'ADMIN') or (sessionScope.userType eq 'CLIENT')}">
				<form action="${pageContext.request.contextPath}/logout" method="GET">
					<input type="hidden" name="command" value="logout" /><br/>
					<button class="btn btn-primary" type="submit">
						<s:message code="usergadget.logout"/> <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
					</button>
				</form>
			</c:if>
	  	</div>
	</div>
</div>