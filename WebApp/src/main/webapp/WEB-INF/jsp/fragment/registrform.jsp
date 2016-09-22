<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/form_validation.js" ></script>

<div id="registrForm">
	<sf:form class="form-horizontal" action="${pageContext.request.contextPath}/users/addnew" method="POST" modelAttribute="user" >
		<h3><s:message code="regform.title" /></h3><br />
		
		<div id="regLogin" class="form-group">
			<sf:errors path="login" cssClass="error" />
			<div class="col-xs-2">
				<label for="loginData" class="control-label"><s:message code="regform.login" /></label>
			</div>
			<div class="col-xs-4">
				<sf:input path="login" id="regLoginData" required="required" placeholder="login" pattern="[a-zA-Z0-9]{6,45}" oninput="checkEnteredData('regLogin')" class="form-control" />
      			<span id="regLoginIcon"></span>
      			<span class="help-block"><s:message code="regform.login.helptext" /></span>
			</div>
    	</div>
     	<div id="regPassword" class="form-group">
     		<sf:errors path="password" cssClass="error" />
			<div class="col-xs-2">
	   			<label for="regPasswordData" class="control-label"><s:message code="regform.password" /></label>
			</div>
			<div class="col-xs-4">
      			<sf:password path="password" id="regPasswordData" required="required" placeholder="password" pattern="[a-zA-Z0-9]{6,45}" oninput="checkEnteredData('regPassword')" class="form-control" />
      			<span id="regPasswordIcon"></span>
      			<span class="help-block"><s:message code="regform.login.helptext" /></span>
			</div>
    	</div>
    	<div id="regEmail" class="form-group">
    		<sf:errors path="email" cssClass="error" />
			<div class="col-xs-2">
	   			<label for="regEmailData" class="control-label"><s:message code="regform.email" /></label>
			</div>
			<div class="col-xs-4">
      			<sf:input path="email" id="regEmailData" type="email" required="required" placeholder="emailaddress@gmail.com" pattern="[a-zA-Z0-9.]+@[a-zA-Z0-9]+\.[a-zA-Z]+" oninput="checkEnteredData('regEmail')" class="form-control" />
      			<span id="emailIcon"></span>
      			<span class="help-block"><s:message code="regform.email.helptext" /></span>
			</div>
    	</div>
    	<div id="firstName" class="form-group">
    		<sf:errors path="firstname" cssClass="error" />
			<div class="col-xs-2">
	   			<label for="firstNameData" class="control-label"><s:message code="regform.firstname" /></label>
			</div>
			<div class="col-xs-4">
      			<sf:input path="firstname" id="firstNameData" required="required" placeholder="firstname" pattern="[a-zA-Zа-яА-ЯёЁ ]+" oninput="checkEnteredData('firstName')" class="form-control" />
      			<span id="firstNameIcon"></span>
      			<span class="help-block"><s:message code="regform.firstname.helptext" /></span>
			</div>
    	</div>
    	<div id="lastName" class="form-group">
    		<sf:errors path="lastname" cssClass="error" />
			<div class="col-xs-2">
	   			<label for="lastNameData" class="control-label"><s:message code="regform.lastname" /></label>
			</div>
			<div class="col-xs-4">
      			<sf:input path="lastname" id="lastNameData" required="required" placeholder="lastname" pattern="[a-zA-Zа-яА-ЯёЁ ]+" oninput="checkEnteredData('lastName')" class="form-control"/>
      			<span id="lastNameIcon"></span>
      			<span class="help-block"><s:message code="regform.lastname.helptext" /></span>
			</div>
    	</div>
    	<div id="shipAddress" class="form-group">
    		<sf:errors path="shippingAddress" cssClass="error" />
			<div class="col-xs-2">
	   			<label for="shipAddressData" class="control-label"><s:message code="regform.shipaddress" /></label>
			</div>
			<div class="col-xs-4">
				<sf:textarea path="shippingAddress" id="shipAddressData" placeholder="Your Shipping Address" required="required" oninput="checkEnteredData('shipAddress')" class="form-control" rows="5"></sf:textarea>
      			<span id="shipAddressIcon"></span>
			</div>
    	</div>
    	<div id="age" class="form-group">
    		<sf:errors path="age" cssClass="error" />
			<div class="col-xs-2">
	   			<label for="ageData" class="control-label"><s:message code="regform.age" /></label>
			</div>
			<div class="col-xs-4">
      			<sf:input path="age" id="ageData" required="required" pattern="[0-9]{0,2}" oninput="checkEnteredData('age')" class="form-control"/>
      			<span id="ageIcon"></span>
      			<span class="help-block"><s:message code="regform.age.helptext"/></span>
			</div>
    	</div>
    	<!-- Buttons -->
    	<span><s:message code="regform.infotext"/></span><br /><br />
		 <button class="btn btn-primary" type="submit">
		 	<span class="glyphicon glyphicon-send" ></span> <s:message code="regform.button.registration"/>
		 </button>
		 <button class="btn btn-default" type="reset">
		 	<s:message code="regform.button.reset"/>
		 </button>
	</sf:form>
</div>