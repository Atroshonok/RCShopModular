<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="orderedProducts" class="table-responsive">
	<table class ="table table-bordered table-striped">
		<caption><b>Ordered products list</b></caption>
			<tr class="info">
				<th>Product</th>
				<th>Price, $</th>
				<th>Count</th>
				<th>Action</th>
			</tr>
		<c:forEach var="orderLine" items="${sessionScope.cart.orderLines}" varStatus="status">
			<tr>
				<td><c:out value="${orderLine.product.name}" /></td>
				<td><c:out value="${orderLine.product.price}" /></td>
				<td><c:out value="${orderLine.count}" /></td>
				<td>
					<form id="removeProductButton" action="${pageContext.request.contextPath}/cart/remove/${orderLine.product.id}" method="GET" >
						<button class="btn btn-info" type="submit">
							<span class="glyphicon glyphicon-minus"></span> <s:message code="orderedproducts.buttons.remove"/>
						</button>
					</form>
				</td>
			</tr>
		</c:forEach>
	</table><br/>
	<p><s:message code="orderedproducts.message.commonprice"/> <b><c:out value="${sessionScope.cart.sumPrice}" /> $</b></p>
	<form id="makeOrder" action="${pageContext.request.contextPath}/orders/add" method="POST">
		<button class="btn btn-primary" type="submit">
			<span class="glyphicon glyphicon-download-alt"></span> <s:message code="orderedproducts.buttons.order"/>
		</button>
	</form>
</div>