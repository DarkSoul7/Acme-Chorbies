<%--
 * action-1.jsp
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="banner">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<acme:textbox code="banner.picture" path="picture" mandatory="true" />
	<spring:message code="banner.pictureDetails" var="pictureDetails" />
	<p style="padding-left:1cm"><font size="4" color="blue"><jstl:out value="${pictureDetails}"/></font></p>
	<br />

	<acme:submit code="banner.save" name="save" />
	<acme:cancel code="banner.cancel" url="banner/list.do" />
</form:form>
