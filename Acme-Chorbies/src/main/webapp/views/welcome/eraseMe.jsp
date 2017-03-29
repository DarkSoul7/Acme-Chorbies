<%--
 * eraseMe.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<body style="text-align: justify;">
	<p>
		<spring:message code="welcome.erase.p1" />
	</p>
	<p>
		<spring:message code="welcome.erase.p2" />
	</p>
	<p>
		<spring:message code="welcome.erase.p3" />
	</p>
	<p>
		<spring:message code="welcome.erase.p4" />
	</p>
	<p>
		<spring:message code="welcome.erase.p5" />
	</p>
	
	<p>
		<spring:message code="welcome.erase.p6" />
	</p>
</body>
<br>
<br>
<br>
 <acme:cancel code="welcome.close" url="${backURI}" />
 
	



</html>