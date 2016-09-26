<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

		
<div class="panel panel-danger">
	<div class="panel-heading">
		<i class="panel-title">Only for Administrator</i>
	</div>
	<div class="panel-body">
		<form id="showAllUsersButton" action="${pageContext.request.contextPath}/users/all" method="GET">
			<input class="btn btn-default btn-block btn-danger" type="submit" value="Show all users" />
		</form><br/>	
	
		<form id="addNewProductButton" action="${pageContext.request.contextPath}/products/new" method="GET">
			<input class="btn btn-default btn-block btn-danger" type="submit" value="Add new product" />
		</form><br/>
		
		<form id="addNewProductCategoryButton" action="${pageContext.request.contextPath}/categories/new" method="GET">
			<input class="btn btn-default btn-block btn-danger" type="submit" value="Add new product category" />
		</form><br/>
	</div>
</div>
