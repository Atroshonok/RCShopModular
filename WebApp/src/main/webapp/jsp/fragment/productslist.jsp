<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="pagelocale"/>

<c:if test="${sessionScope.userType eq 'GUEST'}">
	<h1><fmt:message key="productlist.message.welcome"/></h1><br/>
	<h4 class="alert alert-danger"><fmt:message key="productlist.message.registerplease"/></h4><br/>
</c:if>
<c:if test="${productAddedMessage ne null}">
	<div class="alert alert-success" role="alert">
		${productAddedMessage}	
	</div>
</c:if>
<c:forEach var="product" items="${sessionScope.productsList}" varStatus="status">
	<div class="panel panel-primary">
  		<div class="panel-heading">
    		<h3 class="panel-title"><fmt:message key="productlist.product"/> <c:out value="${product.name}"/> 
    			<span class="badge"><fmt:message key="productlist.price"/> <c:out value="${product.price}"/><span class="glyphicon glyphicon-usd"></span></span>
    		</h3> 
 		</div>
  		<div class="panel-body">
  			<div class="row">
  				<div class="col-md-9">
  					<b><fmt:message key="productlist.description"/></b><br/>
  					<c:out value="${product.description}"/>
  				</div>
  				<div class="col-md-3">
  					<img alt="picture" width="200px" src="./images/producte-${product.id}.jpg">
  				</div>
  			</div>
  			<div id="cartAddButton">
  				<c:if test="${sessionScope.userType eq 'CLIENT' or 'ADMIN'}">
					<form id="addProductButton" action="controller" method="POST" >
						<input type="hidden" name="command" value="addtocart" />
						<input type="hidden" name="productid" value="${product.id}" />
						<input type="hidden" name="productname" value="${product.name}" />
						<input type="hidden" name="productprice" value="${product.price}" />
						<button class="btn btn-primary" type="submit">
							<span class="glyphicon glyphicon-shopping-cart"></span> <fmt:message key="productlist.button.add"/>
						</button>
					</form>
				</c:if>
				<c:if test="${sessionScope.userType eq 'ADMIN'}">
					<form id="editProductButton" action="controller" method="POST" >
						<input type="hidden" name="command" value="editproduct" />
						<input type="hidden" name="productid" value="${product.id}" />
						<button class="btn btn-warning" type="submit">
							<span class="glyphicon glyphicon-edit"></span> Edit
						</button>
					</form>
				</c:if>	
  			</div>
  		</div>
	</div>
</c:forEach>

