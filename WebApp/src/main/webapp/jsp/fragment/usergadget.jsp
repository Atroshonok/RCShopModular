<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="pagelocale"/>

<div id="userGadget">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<c:if test="${sessionScope.userType eq 'GUEST'}">
				<b><fmt:message key="usergadget.hello"/>, <fmt:message key="usergadget.guest"/>!"</b>
			</c:if>
			<c:if test="${(sessionScope.userType eq 'ADMIN') or (sessionScope.userType eq 'CLIENT')}">
				<b><span class="glyphicon glyphicon-user" aria-hidden="true"></span><fmt:message key="usergadget.hello" />, ${sessionScope.userLogin}!</b>
			</c:if>
	  	</div>
	  	<div class="panel-body">
	  		<c:if test="${sessionScope.userType eq 'GUEST'}">
				<c:import url="/jsp/fragment/login.jsp" />
			</c:if>
		  	<c:if test="${sessionScope.userType eq 'CLIENT'}">
				<a href="controller?command=showuserorders"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span> <fmt:message key="usergadget.all.orders"/></a><br/>
				<fmt:message key="usergadget.session.orders"/>: <span class="badge">${sessionScope.cart.getOrders().size()}</span><br/>
				<a href="controller?command=showcart"><span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span> <fmt:message key="usergadget.cart"/></a><br/>
				<fmt:message key="usergadget.cart.products"/>: <span class="badge">${sessionScope.cart.getAllProductsCount()}</span><br/>
			</c:if>
			<c:if test="${(sessionScope.userType eq 'ADMIN') or (sessionScope.userType eq 'CLIENT')}">
				<form action="controller" method="POST">
					<input type="hidden" name="command" value="logout" /><br/>
					<button class="btn btn-primary" type="submit">
						<fmt:message key="usergadget.logout"/> <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
					</button>
				</form>
			</c:if>
	  	</div>
	</div>
</div>