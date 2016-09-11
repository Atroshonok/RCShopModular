<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="pagelocale"/>

<div id="filterForm">
	<form id="productFilter" class="form-inline" action="controller" method="GET">
		<input type="hidden" name="command" value="showproducts">
		<input type="hidden" name="isFilterChanged" value="true">
		
		<strong><fmt:message key="filter.bar.product.category"/></strong> |
		<label class="checkbox-inline">
			<c:choose>
				<c:when test="${!sessionScope.clientFilter.categoriesId.get(0).isEmpty()}">
					<input id="all" type="checkbox" name="filterCategoriesId[0]" value="all" checked /> <fmt:message key="navbar.menuitem.all" /> |
				</c:when>
				<c:otherwise>
					<input id="all" type="checkbox" name="filterCategoriesId[0]" value="all" /> <fmt:message key="navbar.menuitem.all" /> |
				</c:otherwise>
			</c:choose>
		</label>
		<c:forEach var="category" items="${sessionScope.categories}" varStatus="status">
			<label class="checkbox-inline">
				<c:choose>
					<c:when test="${!sessionScope.clientFilter.categoriesId.get(category.id).isEmpty()}">
						<input id="filterCategory${category.id}" type="checkbox" name="filterCategoriesId[${category.id}]" value="${category.id}" checked/> <fmt:message key="navbar.menuitem.${category.id}" /> |
					</c:when>
					<c:otherwise>
						<input id="filterCategory${category.id}" type="checkbox" name="filterCategoriesId[${category.id}]" value="${category.id}"/> <fmt:message key="navbar.menuitem.${category.id}" /> |
					</c:otherwise>
				</c:choose>
			</label>
		</c:forEach>
		<br/>
		<div class="form-group">
			<label><fmt:message key="filter.bar.product.price"/>&nbsp;
				<fmt:message key="filter.bar.price.from"/> <input class="form-control input-sm" type="text" name="filterPriceFrom" size="10" value="${sessionScope.clientFilter.priceFrom}"> -   
				<fmt:message key="filter.bar.price.to"/> <input class="form-control input-sm" type="text" name="filterPriceTo" size="10" value="${sessionScope.clientFilter.priceTo}"> $
			</label>
		</div>
		<br/>
		<label><fmt:message key="filter.bar.itemsperpage"/>&nbsp;
			<select name="itemsPerPage" class="form-control input-sm" size="1">
				<option>${sessionScope.clientFilter.itemsPerPage}</option>
				<option>1</option>
				<option>3</option>
				<option>6</option>
			</select>
		</label>
		<br/>
		<label><fmt:message key="filter.bar.sortby"/>&nbsp;
			<c:set var="sort" value="${sessionScope.clientFilter.sorting}" scope="page"/>
			<select name="sorting" class="form-control input-sm" size="1">
				<option value="${sort}">
					<c:choose>
						<c:when test="${sort == 0}"><fmt:message key="filter.bar.sort.price.nosort"/></c:when>
						<c:when test="${sort == 1}"><fmt:message key="filter.bar.sort.price.lowtohigh"/></c:when>
						<c:when test="${sort == 2}"><fmt:message key="filter.bar.sort.price.hightolow"/></c:when>
						<c:when test="${sort == 3}"><fmt:message key="filter.bar.sort.price.category"/></c:when>
					</c:choose>
				</option>
				<option value="0"><fmt:message key="filter.bar.sort.price.nosort"/></option>
				<option value="1"><fmt:message key="filter.bar.sort.price.lowtohigh"/></option>
				<option value="2"><fmt:message key="filter.bar.sort.price.hightolow"/></option>
				<option value="3"><fmt:message key="filter.bar.sort.price.category"/></option>
			</select>
		</label>
		<br/>
		<button type="submit" class="btn btn-primary">
			<fmt:message key="filter.bar.button.accept" /> <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
		</button>
		<button type="reset" class="btn btn-default">
			<fmt:message key="filter.bar.button.reset" /> <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
		</button>
	</form>
	<hr/>
</div>

<c:if test="${sessionScope.userType eq 'GUEST'}">
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
<div>
	<c:import url="/jsp/fragment/pagination.jsp"></c:import>
</div>

