/**
 * 
 */

var activePage = document.getElementById("activeCurrentPage").getAttribute("value");
window.onload = makePaginationAnimation;

function makePaginationAnimation(){
	setActiveStyleAttribute("currentPage" + activePage);
}

function setActiveStyleAttribute(elemId){
	document.getElementById(elemId).setAttribute("class", "active");
}

