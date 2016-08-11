<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="table-responsive">
	<table class ="table table-bordered table-striped">
	<caption><b>All products</b></caption>
		<tr class="info">
			<th>ID</th>
			<th>Name</th>
			<th>Price, $</th>
			<th>Category ID</th>
			<th>Count</th>
			<th>Description</th>
			<th>Action</th>
		</tr>
	<c:forEach var="product" items="${sessionScope.productsList}" varStatus="status">
		<tr>
			<td><c:out value="${product.id}"/></td>
			<td><c:out value="${product.name}"/></td>
			<td><c:out value="${product.price}"/></td>
			<td><c:out value="${product.category.id}"/></td>
			<td><c:out value="${product.count}"/></td>
			<td>
				<c:if test="${product.description.length() gt 50}">
					<c:out value="${product.description.substring(0, 50)} ..."  />
				</c:if>
				<c:if test="${product.description.length() <= 50}">
					<c:out value="${product.description}"  />
				</c:if>
			</td>
			<td>
				<form id="editProductButton" action="controller" method="POST" >
					<input type="hidden" name="command" value="editproduct" />
					<input type="hidden" name="productid" value="${product.id}" />
					<button class="btn btn-warning" type="submit">
						<span class="glyphicon glyphicon-edit"></span> Edit
					</button>
				</form>
			</td>
		</tr>
	</c:forEach>
	</table>
</div>