<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="pagelocale"/>


<script type="text/javascript" src="js/navbar_animation.js"></script>
<input id="activePage" type="hidden" value="${activePage}"/>
<div id="navbar">
	<ul class="nav nav-pills nav-stacked" >
		<li id="menuItemMain"><a href="controller?command=getmainpage"><fmt:message key="navbar.menuitem.main" /></a></li>
		<li id="menuItemProducts"><a href="controller?command=showproducts"><fmt:message key="navbar.menuitem.products" /></a></li>
		<c:forEach var="category" items="${sessionScope.categories}" varStatus="status">
			<li id="menuItem${category.id}"><a href="controller?command=showproducts&filterCategoriesId[${category.id}]=${category.id}"><fmt:message key="navbar.menuitem.${category.id}" /></a></li>
		</c:forEach>
	</ul>
</div>
<br/>
<div id="adminsidebar">
	<c:if test="${sessionScope.userType eq 'ADMIN'}">
		<c:import url="/jsp/fragment/adminsidebar.jsp" />
	</c:if>
</div>