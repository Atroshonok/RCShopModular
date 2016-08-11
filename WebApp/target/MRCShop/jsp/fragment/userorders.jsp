<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="table-responsive">
	<table class ="table table-bordered table-striped">
		<caption><b>Orders</b></caption>
			<tr class="info">
				<th>Order ID</th>
				<th>Common Price, $</th>
				<th>State</th>
				<th>Products List</th>
			</tr>
		<c:forEach var="order" items="${orders}" varStatus="status">
			<tr>
				<td><c:out value="${order.id}" /></td>
				<td><c:out value="${order.sumPrice}" /></td>
				<td><c:out value="${order.state}" /></td>
				<td>
					<c:forEach var="productsPair" items="${order.orderedProducts.entrySet()}" >
						<c:out value="[${productsPair.getKey().getName()} - " />
						<c:out value="${productsPair.getValue()} pc.] " />
					</c:forEach>
				</td>
			</tr>
		</c:forEach>
	</table>
 </div>
