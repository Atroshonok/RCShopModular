<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="deleteProductForm">
	<table>
		<tr>
			<td>
				<form
					action="${pageContext.request.contextPath}/products/delete/${product.id}" method="POST">
					<input type="hidden" name="answer" value="">
					<button type="submit" class="btn btn-danger">
						<span class="glyphicon glyphicon-trash"></span> Delete
					</button>
					&nbsp
				</form>
			</td>
			<td>
				<form action="${pageContext.request.contextPath}/products/all" method="GET">
					<button type="submit" class="btn btn-info">
						<span class="glyphicon glyphicon-repeat"></span> Cancel
					</button>
				</form>
			</td>
		</tr>
	</table>
</div>
