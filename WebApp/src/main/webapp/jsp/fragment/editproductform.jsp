<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="editProductForm">
	<form class="form-horizontal" action="controller" method="POST" title="Edit product form" >
		<input type="hidden" name="command" value="saveeditedproduct" />
		<h3>Edit product form</h3><br/>
		
		<div class="form-group">
			<div class="col-xs-2">
	   			<label for="productIdData" class="control-label">Product ID:</label>
			</div>
			<div class="col-xs-4">
				<input id="productIdData" class="form-control" type="text" name="id" required="required" value="${product.id}" contenteditable="false" />
			</div>
    	</div>
    	<div class="form-group">
			<div class="col-xs-2">
	   			<label for="productNameData" class="control-label">Product Name:</label>
			</div>
			<div class="col-xs-4">
				<input id="productNameData" class="form-control" type="text" name="name" required="required" value="${product.name}" />
			</div>
    	</div>
    	<div class="form-group">
			<div class="col-xs-2">
	   			<label for="productPriceData" class="control-label">Product Price:</label>
			</div>
			<div class="col-xs-4">
				<input id="productPriceData" class="form-control" type="text" name="price" required="required" value="${product.price}" pattern="[1-9][0-9]+\.[0-9]{1,2}"/>
			</div>
    	</div>
    	    	
    	
    	<div class="form-group">
			<div class="col-xs-2">
	   			<label for="productCategoryData" class="control-label">Category ID:</label>
			</div>
			<div class="col-xs-4">
				<input id="productCategoryData" class="form-control" type="text" name="categoryID" required="required" value="${product.category.id}" pattern="[0-9]+"/>
			</div>
    	</div>
    	
    	
    	<div class="form-group">
			<div class="col-xs-2">
	   			<label for="productCountData" class="control-label">Product count:</label>
			</div>
			<div class="col-xs-4">
				<input id="productCountData" class="form-control" type="text" name="count" required="required" value="${product.count}" pattern="[0-9]+"/>
			</div>
    	</div>
    	<div class="form-group">
			<div class="col-xs-2">
	   			<label for="productDescriptData" class="control-label">Product description:</label>
			</div>
			<div class="col-xs-4">
				<textarea id="productDescriptData" name="description" rows="10" cols="50" contenteditable="true" >${product.description}</textarea> 
			</div>
    	</div>
    	<button type="submit" class="btn btn-danger">
    		<span class="glyphicon glyphicon-floppy-disk"></span> Save
    	</button>   	
	</form>
</div>