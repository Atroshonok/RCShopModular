/**
 * Navbar Animation
 */

window.onload = setActiveElement;

function setActiveElement(){
	var activeElemId = document.getElementById("activePage").getAttribute("value");
	console.log(activeElemId);
	setActiveStyleAttribute("item_" + activeElemId);
}

function setActiveStyleAttribute(elemID){
	document.getElementById(elemID).setAttribute("class", "active");
}