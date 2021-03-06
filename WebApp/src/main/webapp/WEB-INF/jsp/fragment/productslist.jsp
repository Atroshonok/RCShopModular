<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<div id="filterForm">
	<form id="productFilter" class="form-inline" action="${pageContext.request.contextPath}/products/all" method="GET" >
		<input type="hidden" name="isFilterChanged" value="true">
		
		<strong><s:message code="filter.bar.product.category"/></strong> |
		<c:forEach var="category" items="${sessionScope.categories}" varStatus="status" >
			<label class="checkbox-inline">
				<c:choose>
					<c:when test="${sessionScope.clientFilter.filterCategoriesId.contains(category.id)}">
						<input id="filterCategory${category.id - 1}" type="checkbox" name="filterCategoriesId[${category.id - 1}]" value="${category.id}" checked/> <s:message  code="navbar.menuitem.${category.id}" text="${category.categoryName}" /> |
					</c:when>
					<c:otherwise>
						<input id="filterCategory${category.id - 1}" type="checkbox" name="filterCategoriesId[${category.id - 1}]" value="${category.id}"/> <s:message code="navbar.menuitem.${category.id}" text="${category.categoryName}" /> |
					</c:otherwise>
				</c:choose>
			</label>
		</c:forEach>
		<br/>
		<div class="form-group">
			<label><s:message code="filter.bar.product.price"/>&nbsp;
				<s:message code="filter.bar.price.from"/> <input class="form-control input-sm" type="text" name="filterPriceFrom" size="10" value="${sessionScope.clientFilter.filterPriceFrom}"> -   
				<s:message code="filter.bar.price.to"/> <input class="form-control input-sm" type="text" name="filterPriceTo" size="10" value="${sessionScope.clientFilter.filterPriceTo}"> $
			</label>
		</div>
		<br/>
		<label><s:message code="filter.bar.itemsperpage"/>&nbsp;
			<select name="itemsPerPage" class="form-control input-sm" size="1">
				<option>${sessionScope.clientFilter.itemsPerPage}</option>
				<option>1</option>
				<option>3</option>
				<option>6</option>
			</select>
		</label>
		<br/>
		<label><s:message code="filter.bar.sortby"/>&nbsp;
			<c:set var="sort" value="${sessionScope.clientFilter.sorting}" scope="page"/>
			<select name="sorting" class="form-control input-sm" size="1">
				<option value="${sort}">
					<c:choose>
						<c:when test="${sort == 0}"><s:message code="filter.bar.sort.price.nosort"/></c:when>
						<c:when test="${sort == 1}"><s:message code="filter.bar.sort.price.lowtohigh"/></c:when>
						<c:when test="${sort == 2}"><s:message code="filter.bar.sort.price.hightolow"/></c:when>
						<c:when test="${sort == 3}"><s:message code="filter.bar.sort.price.category"/></c:when>
					</c:choose>
				</option>
				<option value="0"><s:message code="filter.bar.sort.price.nosort"/></option>
				<option value="1"><s:message code="filter.bar.sort.price.lowtohigh"/></option>
				<option value="2"><s:message code="filter.bar.sort.price.hightolow"/></option>
				<option value="3"><s:message code="filter.bar.sort.price.category"/></option>
			</select>
		</label>
		<br/>
		<button type="submit" class="btn btn-primary">
			<span class="glyphicon glyphicon-ok" aria-hidden="true"></span> 
			<s:message code="filter.bar.button.accept" />
		</button>
		<button type="submit" class="btn btn-default" form="resetFilterForm">
			<span class="glyphicon glyphicon-refresh" aria-hidden="true"></span> 
			<s:message code="filter.bar.button.reset" />
		</button>
	</form>
	<form id="resetFilterForm" action="${pageContext.request.contextPath}/products/reset" method="GET"></form>
	<hr/>
</div>

<div id="productItemsConainer">
	<c:if test="${sessionScope.userType eq 'GUEST'}">
		<h4 class="alert alert-danger"><s:message code="message.registerplease"/></h4><br/>
	</c:if>
	<c:if test="${infoMessage ne null}">
		<div class="alert alert-success" role="alert">
			${infoMessage}	
		</div>
	</c:if>
	
	<div id="productItem">
		<c:forEach var="product" items="${sessionScope.productsList}" varStatus="status">
			<div class="panel panel-primary">
		  		<div class="panel-heading">
		    		<h3 class="panel-title"><s:message code="productlist.product"/> <c:out value="${product.name}"/> 
		    			<span class="badge"><s:message code="productlist.price"/> <c:out value="${product.price}"/><span class="glyphicon glyphicon-usd"></span></span>
		    		</h3> 
		 		</div>
		  		<div class="panel-body">
		  			<div class="row">
		  				<div class="col-md-8">
		  					<b><s:message code="productlist.description"/></b><br/>
		  					<c:out value="${product.description}"/>
		  				</div>
		  				<div class="col-md-4">
		  					<img alt="picture-${product.id}" width="200px" src="${pageContext.request.contextPath}/resources/images/product-${product.id}.jpg">
		  				</div>
		  			</div>
		  			<div id="cartAddButton">
		  				<c:if test="${sessionScope.userType eq 'CLIENT' or 'ADMIN'}">
							<form id="addProductButton" action="${pageContext.request.contextPath}/cart/add/${product.id}" method="GET" >
								<input type="hidden" name="command" value="addtocart" />
								<button class="btn btn-primary" type="submit">
									<span class="glyphicon glyphicon-shopping-cart"></span> <s:message code="productlist.button.add"/>
								</button>
							</form>
						</c:if>
						<c:if test="${sessionScope.userType eq 'ADMIN'}">
							<table>
								<tr>
									<td>
										<form id="editProductButton" action="${pageContext.request.contextPath}/products/edit/${product.id}" method="GET" >
											<button class="btn btn-warning" type="submit">
												<span class="glyphicon glyphicon-edit"></span> <s:message code="productlist.button.edit" />
											</button>
											&nbsp
										</form>
									</td>
									<td>
										<form id="deleteProductButton" action="${pageContext.request.contextPath}/products/delete/${product.id}" method="GET" >
											<button class="btn btn-danger" type="submit">
												<span class="glyphicon glyphicon-trash"></span> <s:message code="productlist.button.delete" />
											</button>
										</form>
									</td>	
								</tr>
							</table>
						</c:if>	
		  			</div>
		  		</div>
			</div>
		</c:forEach>
	</div>
	<div>
		<c:import url="/WEB-INF/jsp/fragment/pagination.jsp"></c:import>
	</div>
</div>

