function relativeRedir(loc) {
	var b = document.getElementsByTagName('base');
	if (b && b[0] && b[0].href) {
		if (b[0].href.substr(b[0].href.length - 1) == '/' && loc.charAt(0) == '/')
			loc = loc.substr(1);
		loc = b[0].href + loc;
	}
	window.location.replace(loc);
}

function confirmDeletion(language, uri) {
	var question;
	if (language == 'es') {
		question = "¿Desea confirmar el borrado?";
	} else {
		question = "Do you want to confirm the deletion?";
	}
	var c = confirm(question);
	if (c == true) {
		window.location.replace(uri);
	}
}
