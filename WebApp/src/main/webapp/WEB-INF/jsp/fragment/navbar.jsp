<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/navbar_animation.js"></script>

<input id="activePage" type="hidden" value="${activePage}"/>
<div id="navbar">
	<ul class="nav nav-pills nav-stacked" >
		<li id="menuItemMain">
			<a href="${pageContext.request.contextPath}/main">
				<s:message code="navbar.menuitem.main" />
			</a>
		</li>
		<li id="menuItemProducts">
			<a href="${pageContext.request.contextPath}/products/all">
				<s:message code="navbar.menuitem.products" />
			</a>
		</li>
		<c:forEach var="category" items="${sessionScope.categories}" varStatus="status">
			<li id="menuItem${category.id}">
				<a href="${pageContext.request.contextPath}/products/all?filterCategoriesId[${category.id}]=${category.id}">
					<s:message code="navbar.menuitem.${category.id}" />
				</a>
			</li>
		</c:forEach>
	</ul>
</div>
<br/>
<div id="adminsidebar">
	<c:if test="${sessionScope.userType eq 'ADMIN'}">
		<c:import url="/WEB-INF/jsp/fragment/adminsidebar.jsp" />
	</c:if>
</div>