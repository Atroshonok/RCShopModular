<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="table-responsive">
	<table class ="table table-bordered table-striped ">
	<caption><b>All users</b></caption>
		<tr class="info">
			<th>ID</th>
			<th>Registr. date</th>
			<th>Login</th>
			<th>Email</th>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Shipping Address</th>
			<th>Age</th>
			<th>User Type</th>
			<th>Black List (BL)</th>
			<th>Action</th>
		</tr>
	<c:forEach var="user" items="${usersList}" varStatus="status">
		<tr>
			<td><c:out value="${user.id}"/></td>
			<td><c:out value="${user.registrDate}"/></td>
			<td><c:out value="${user.login}"/></td>
			<td><c:out value="${user.email}"/></td>
			<td><c:out value="${user.firstname}"/></td>
			<td><c:out value="${user.lastname}"/></td>
			<td>
				<c:if test="${user.shippingAddress.length() > 50}">
					<c:out value="${user.shippingAddress.substring(0, 50)} ..."  />
				</c:if>
				<c:if test="${user.shippingAddress.length() <= 50}">
					<c:out value="${user.shippingAddress}"  />
				</c:if>
			</td>
			<td><c:out value="${user.age}"/></td>
			<td><c:out value="${user.role}"/></td>
			<td><c:out value="${user.isInBlackList()}"/></td>
			<td>
				<form id="addInBlackListButton" action="controller" method="POST" >
					<input type="hidden" name="command" value="blacklist" />
					<input type="hidden" name="action" value="add" />
					<input type="hidden" name="userid" value="${user.id}" />
					<button class="btn btn-default btn-block btn-danger" type="submit">
						<span class="glyphicon glyphicon-plus"></span> Black List
					</button>
				</form>
				<form id="removeFromBlackListButton" action="controller" method="POST" >
					<input type="hidden" name="command" value="blacklist" />
					<input type="hidden" name="action" value="remove" />
					<input type="hidden" name="userid" value="${user.id}" />
					<button class="btn btn-default btn-block btn-warning" type="submit">
						<span class="glyphicon glyphicon-minus"></span> Black List
					</button>
				</form>
			</td>
		</tr>
	</c:forEach>
	</table>
</div>