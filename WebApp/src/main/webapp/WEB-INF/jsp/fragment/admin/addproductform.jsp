<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="addProductForm">
	<sf:form class="form-horizontal" modelAttribute="newProduct" action="${pageContext.request.contextPath}/products/new" method="POST" enctype="multipart/form-data" title="Add product form" >
		<input type="hidden" name="command" value="saveproduct" />
		<h3>Add product form</h3><br/>
    	
    	<div class="form-group">
			<div class="col-xs-3">
	   			<label for="productNameData" class="control-label">Product Name:</label>
			</div>
			<div class="col-xs-5">
				<sf:input id="productNameData" path="name" class="form-control" type="text" name="name" required="required" />
			</div>
    	</div>
    	<div class="form-group">
			<div class="col-xs-3">
	   			<label for="productPriceData" class="control-label">Product Price:</label>
			</div>
			<div class="col-xs-5">
				<sf:input id="productPriceData" path="price" class="form-control" type="text" name="price" required="required" pattern="[0-9]+\.?[0-9]?{1,2}"/>
			</div>
    	</div>
    	
    	<!-- Product Category -->
    	<div class="form-group">
			<div class="col-xs-3">
	   			<label for="productCategoryData" class="control-label">Category:</label>
			</div>
			<div class="col-xs-5">
				<sf:select id="productCategoryData" path="productCategoryId" class="form-control" size="1">
					<c:forEach var="productCategory" items="${categoryList}">
						<sf:option label="${productCategory.categoryName}" value="${productCategory.id}" />
					</c:forEach>
				</sf:select>
			</div>
    	</div>
    	<div class="form-group">
			<div class="col-xs-3">
	   			<label for="productCountData" class="control-label">Product count:</label>
			</div>
			<div class="col-xs-5">
				<sf:input id="productCountData" path="count" class="form-control" type="text" name="count" required="required" pattern="[0-9]+"/>
			</div>
    	</div>
    	<div class="form-group">
			<div class="col-xs-3">
	   			<label for="productDescriptData" class="control-label">Product description:</label>
			</div>
			<div class="col-xs-5">
				<sf:textarea id="productDescriptData" path="description" class="form-control" name="description" rows="10" cols="50"></sf:textarea> 
			</div>
    	</div>
    	<div class="form-group">
    		<label for="inputFile">Product image: </label>
    		<input type="file" id="inputFile" name="productImage">
    		<p class="help-block">Load an image for the product.</p>
 		 </div>
    	
    	<button type="submit" class="btn btn-danger">
    		<span class="glyphicon glyphicon-floppy-disk"></span> Save
    	</button>  
    </sf:form> 	
</div>