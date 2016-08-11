function checkEnteredData(inputID) {
	var pattern = null;

	switch (inputID) {
	case 'regLogin':
		pattern = new RegExp('^[a-zA-Z0-9]{6,45}$', '');
		break;

	case 'regPassword':
		pattern = new RegExp('^[a-zA-Z0-9]{6,45}$', '');
		break;

	case 'email':
		pattern = new RegExp('^[a-zA-Z0-9.]+@[a-zA-Z0-9]+\\.[a-zA-Z]+$', '');
		break;

	case 'firstName':
		pattern = new RegExp('^[a-zA-Zа-яА-ЯёЁ ]+$', '');
		break;

	case 'lastName':
		pattern = new RegExp('^[a-zA-Zа-яА-ЯёЁ ]+$', '');
		break;

	case 'shipAddress':
		pattern = new RegExp('^.+$', '');
		break;

	case 'age':
		pattern = new RegExp('^[0-9]{1,3}$', '');
		break;

	}

	var inputStr = document.getElementById(inputID + 'Data').value;

	if (pattern.test(inputStr)) {
		setSuccesAttributes(inputID);
	} else {
		setErrorAttributes(inputID);
	}
}

function setErrorAttributes(inputID) {
	document.getElementById(inputID).setAttribute("class", "form-group has-error has-feedback");
	document.getElementById(inputID + 'Icon').setAttribute("class", "glyphicon glyphicon-remove form-control-feedback");
}

function setSuccesAttributes(inputID) {
	document.getElementById(inputID).setAttribute("class", "form-group has-success has-feedback");
	document.getElementById(inputID + 'Icon').setAttribute("class", "glyphicon glyphicon-ok form-control-feedback");
}