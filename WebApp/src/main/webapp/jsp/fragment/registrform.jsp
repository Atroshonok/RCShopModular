<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:setBundle basename="pagelocale"/>
<script type="text/javascript" src="js/form_validation.js" ></script>

<div id="registrForm">
	<form class="form-horizontal" action="controller" method="POST" title="Registration form" >
		<input type="hidden" name="command" value="registration" />
		<h3><fmt:message key="regform.title"/></h3><br/>
		
		<div id="regLogin" class="form-group">
			<div class="col-xs-2">
	   			<label for="loginData" class="control-label"><fmt:message key="regform.login"/></label>
			</div>
			<div class="col-xs-4">
      			<input id="regLoginData" type="text" name="login" required="required" placeholder="login" pattern="[a-zA-Z0-9]{6,45}" onselect="checkEnteredData('regLogin')" oninput="checkEnteredData('regLogin')" class="form-control" />
      			<span id="regLoginIcon"></span>
      			<span class="help-block"><fmt:message key="regform.login.helptext"/></span>
			</div>
    	</div>
    	
    	<div id="regPassword" class="form-group">
			<div class="col-xs-2">
	   			<label for="regPasswordData" class="control-label"><fmt:message key="regform.password"/></label>
			</div>
			<div class="col-xs-4">
      			<input id="regPasswordData" type="password" name="password" required="required" placeholder="password" pattern="[a-zA-Z0-9]{6,45}" oninput="checkEnteredData('regPassword')" class="form-control"/>
      			<span id="regPasswordIcon"></span>
      			<span class="help-block"><fmt:message key="regform.login.helptext"/></span>
			</div>
    	</div>
		
		<div id="email" class="form-group">
			<div class="col-xs-2">
	   			<label for="emailData" class="control-label"><fmt:message key="regform.email"/></label>
			</div>
			<div class="col-xs-4">
      			<input id="emailData" type="email" name="email" required="required" placeholder="emailaddress@gmail.com" pattern="[a-zA-Z0-9.]+@[a-zA-Z0-9]+\.[a-zA-Z]+" oninput="checkEnteredData('email')" class="form-control"/>
      			<span id="emailIcon"></span>
      			<span class="help-block"><fmt:message key="regform.email.helptext"/></span>
			</div>
    	</div>
    	
    	<div id="firstName" class="form-group">
			<div class="col-xs-2">
	   			<label for="firstNameData" class="control-label"><fmt:message key="regform.firstname"/></label>
			</div>
			<div class="col-xs-4">
      			<input id="firstNameData" type="text" name="firstName" required="required" placeholder="first name" pattern="[a-zA-Zа-яА-ЯёЁ ]+" oninput="checkEnteredData('firstName')" class="form-control"/>
      			<span id="firstNameIcon"></span>
      			<span class="help-block"><fmt:message key="regform.firstname.helptext"/></span>
			</div>
    	</div>
    	
    	<div id="lastName" class="form-group">
			<div class="col-xs-2">
	   			<label for="lastNameData" class="control-label"><fmt:message key="regform.lastname"/></label>
			</div>
			<div class="col-xs-4">
      			<input id="lastNameData" type="text" name="lastName" required="required" placeholder="last name" pattern="[a-zA-Zа-яА-ЯёЁ ]+" oninput="checkEnteredData('lastName')" class="form-control"/>
      			<span id="lastNameIcon"></span>
      			<span class="help-block"><fmt:message key="regform.lastname.helptext"/></span>
			</div>
    	</div>
    	
    	<div id="shipAddress" class="form-group">
			<div class="col-xs-2">
	   			<label for="shipAddressData" class="control-label"><fmt:message key="regform.shipaddress"/></label>
			</div>
			<div class="col-xs-4">
				<textarea id="shipAddressData" name="shipAddress" placeholder="Your Shipping Address" required="required" oninput="checkEnteredData('shipAddress')" class="form-control" rows="5"></textarea>
      			<span id="shipAddressIcon"></span>
			</div>
    	</div>
    	
    	<div id="age" class="form-group">
			<div class="col-xs-2">
	   			<label for="ageData" class="control-label"><fmt:message key="regform.age"/></label>
			</div>
			<div class="col-xs-4">
      			<input id="ageData" type="text" name="age" required="required" pattern="[0-9]{0,3}" oninput="checkEnteredData('age')" class="form-control"/>
      			<span id="ageIcon"></span>
      			<span class="help-block"><fmt:message key="regform.age.helptext"/></span>
			</div>
    	</div>
		
		<span><fmt:message key="regform.infotext"/></span><br/><br/>
		 <button class="btn btn-primary" type="submit">
		 	<span class="glyphicon glyphicon-send" ></span> <fmt:message key="regform.button.registration"/>
		 </button>
		 <button class="btn btn-default" type="reset">
		 	<fmt:message key="regform.button.reset"/>
		 </button>
	</form>
</div>