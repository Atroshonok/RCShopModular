<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="pagelocale" />

<c:set var="pagesList" value="${sessionScope.pageNumberList}" scope="page"/>
<c:set var="activeCurrentPage" value="${sessionScope.clientFilter.currentPage}" scope="page"/>
<input id="activeCurrentPage" type="hidden" value="${activeCurrentPage}">

<nav aria-label="Page navigation">
	<ul class="pagination pagination-sm">
		<li><a href="controller?command=showproducts&currentPage=${pagesList[0]}" aria-label="First page"><span aria-hidden="true"><fmt:message key="pagination.nav.first.page"/></span></a></li>
			<c:choose>
				<c:when test="${activeCurrentPage > 1}">
					<li><a href="controller?command=showproducts&currentPage=${activeCurrentPage - 1}" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
				</c:when>
				<c:otherwise>
					<li class="disabled"><span>&laquo;</span></li>
				</c:otherwise>
			</c:choose>
			
		<%-- Pagination display logic --%>
		<c:choose>
			<c:when test="${activeCurrentPage - 4 > 0}"><c:set var="startPage" value="${activeCurrentPage - 4}" scope="page"/></c:when>
			<c:otherwise><c:set var="startPage" value="0" scope="page"/></c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${activeCurrentPage < pagesList.size() - 4}"><c:set var="endPage" value="${activeCurrentPage + 2}" scope="page"/></c:when>
			<c:otherwise><c:set var="endPage" value="${pagesList.size() - 1}" scope="page"/></c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${pagesList.size() >= 7}">
				<c:if test="${activeCurrentPage > 4}"><li><span>...</span></c:if>
					<c:forEach var="i" items="${pagesList}" begin="${startPage}" end="${endPage}" varStatus="status">
						<li id="currentPage${i}"><a href="controller?command=showproducts&currentPage=${i}">${i}</a></li>
					</c:forEach>
				<c:if test="${activeCurrentPage < pagesList.size() - 4}"><li><span>...</span></c:if>
			</c:when>
			<c:otherwise>
				<c:forEach var="i" items="${pagesList}" varStatus="status">
					<li id="currentPage${i}"><a href="controller?command=showproducts&currentPage=${i}">${i}</a></li>
				</c:forEach>
			</c:otherwise>
		</c:choose>
		<%-- End of pagination display logic --%>
		
		<c:choose>
			<c:when test="${activeCurrentPage < pagesList.size()}">
				<li><a href="controller?command=showproducts&currentPage=${activeCurrentPage + 1}" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
			</c:when>
			<c:otherwise>
				<li class="disabled"><span>&raquo;</span></li>
			</c:otherwise>
		</c:choose>
		<li><a href="controller?command=showproducts&currentPage=${pagesList.size()}" aria-label="Last page"><span aria-hidden="true"><fmt:message key="pagination.nav.last.page"/></span></a></li>
	</ul>
</nav>
<script type="text/javascript" src="js/pagination_animation.js"></script>