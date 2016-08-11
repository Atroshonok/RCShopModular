<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="pagelocale"/>

<div id="orderedProducts" class="table-responsive">
	<table class ="table table-bordered table-striped">
		<caption><b>Ordered products list</b></caption>
			<tr class="info">
				<th>Product</th>
				<th>Price, $</th>
				<th>Count</th>
				<th>Action</th>
			</tr>
		<c:forEach var="product" items="${sessionScope.cart.orderedProducts.keySet()}" varStatus="status">
			<tr>
				<td><c:out value="${product.name}" /></td>
				<td><c:out value="${product.price}" /></td>
				<c:set var="count" value="${sessionScope.cart.orderedProducts.get(product)}" />
				<td><c:out value="${count}" /></td>
				<td>
				<form id="removeProductButton" action="controller" method="POST" >
					<input type="hidden" name="command" value="removefromcart" />
					<input type="hidden" name="productid" value="${product.id}" />
					<input type="hidden" name="productname" value="${product.name}" />
					<input type="hidden" name="productprice" value="${product.price}" />
					<button class="btn btn-info" type="submit">
						<span class="glyphicon glyphicon-minus"></span> <fmt:message key="orderedproducts.buttons.remove"/>
					</button>
				</form>
				</td>
			</tr>
		</c:forEach>
	</table><br/>
	<p><fmt:message key="orderedproducts.message.commonprice"/> <b><c:out value="${sessionScope.cart.sumPrice}" /> $</b></p>
	<form id="makeOrder" action="controller" method="POST">
		<input type="hidden" name="command" value="order" />
		<button class="btn btn-primary" type="submit">
			<span class="glyphicon glyphicon-download-alt"></span> <fmt:message key="orderedproducts.buttons.order"/>
		</button>
	</form>
</div>