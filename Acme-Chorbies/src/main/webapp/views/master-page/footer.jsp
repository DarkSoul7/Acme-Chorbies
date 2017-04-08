<%--
 * footer.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jsp:useBean id="date" class="java.util.Date" />

<hr />

<b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" />
	Acme-Chorbies Co., Inc.
</b>

<div id="botonMostrarBarra" style="display: block;">
	<input type="button" value="<spring:message code="master.page.mostrarBarraCookies" />" onClick="abrirBarra();" class="btn btn-default">
	<acme:cancel code="master.page.eraseMe" url="/welcome/eraseMe.do" />
</div>


<div id="barraCookies" style="display: none; position: fixed; left: 0px; right: 0px; bottom: 0px; width: 100%; min-height: 40px; background: #333333; color: #dddddd; z-index: 99999;">
	<div
		style="width: 100%; position: absolute; padding-left: 5px; font-family: verdana; font-size: 12px; top: 30%;">
		<spring:message code="master.page.barraCookies1" />
		<a href="javascript:void(0);" style="padding: 4px; background: #4682B4; text-decoration: none; color: #fff;" onclick="CerrarBarra();"><b>OK</b></a> <a href="welcome/cookies.do"
			target="_blank" style="padding-left: 5px; text-decoration: none; color: #ffffff;"><spring:message code="master.page.barraCookies2" /></a>
	</div>
</div>

<script type="text/javascript">
	function CerrarBarra() {
		document.getElementById("barraCookies").style.display = "none";//ocultamos la barra de aviso
		document.getElementById("botonMostrarBarra").style.display = "block";

	}
	function abrirBarra() {
		document.getElementById("botonMostrarBarra").style.display = "none";
		document.getElementById("barraCookies").style.display = "block";//mostramos la barra de aviso

	}
	function CerrarInfoFooter() {
		document.getElementById("barraCookies").style.display = "none";//ocultamos la barra de aviso
		document.getElementById("botonMostrarBarra").style.display = "none";

	}
	if (document.URL.indexOf("cookies.do") >= 0) {
		CerrarInfoFooter();
	}
</script>