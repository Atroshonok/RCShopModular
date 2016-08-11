<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="pagelocale"/>

<script type="text/javascript" src="js/navbar_animation.js"></script>

<div id="navbar">
	<ul class="nav nav-pills nav-stacked">
		<li id="mainItem" role="presentation" onclick="setActiveStyleAttribute('mainItem')"><a href="controller?command=getmainpage"><fmt:message key="navbar.main" /></a></li>
		<li id="rccarsItem" role="presentation" onclick="setActiveStyleAttribute('rccarsItem')"><a href="controller?command=showproducts&categoryid=1"><fmt:message key="navbar.rccars" /></a></li>
		<li id="rcplanesItem" role="presentation" onclick="setActiveStyleAttribute('rcplanesItem')"><a href="controller?command=showproducts&categoryid=2"><fmt:message key="navbar.rcplanes" /></a></li>
		<li id="fpvItem" role="presentation" onclick="setActiveStyleAttribute('fpvItem')"><a href="controller?command=showproducts&categoryid=3"><fmt:message key="navbar.fpv" /></a></li>
		<li id="otherItem" role="presentation" onclick="setActiveStyleAttribute('otherItem')"><a href="controller?command=showproducts&categoryid=4"><fmt:message key="navbar.other" /></a></li>
	</ul>
</div>
<br/>
<div id="adminsidebar">
	<c:if test="${sessionScope.userType eq 'ADMIN'}">
		<c:import url="/jsp/fragment/adminsidebar.jsp" />
	</c:if>
</div>