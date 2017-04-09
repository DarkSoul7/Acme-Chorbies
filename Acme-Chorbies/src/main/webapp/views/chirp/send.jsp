<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${requestURI}" modelAttribute="chirpForm">
	<form:hidden path="parentChirpId"/>
	
	<acme:textbox code="chirp.subject" path="subject" mandatory="true" />
	<br/>
	<acme:textarea code="chirp.text" path="text" mandatory="true" />
	<br/>
	<acme:textarea code="chirp.attachments" path="attachments" />
	
	<spring:message code="chirp.attachmentsHelp" var="attachmentsHelp" />
	<p style="padding-left:1cm"><font size="4" color="blue"><jstl:out value="(${attachmentsHelp})"/></font></p>
	<br/>
	<acme:select items="${chorbies}" itemLabel="email" code="chirp.receiver" path="receiver" mandatory="true" />
		
	<br/>
	
	<jstl:if test="${true}">
		<acme:submit code="chirp.send" name="save"/>
	
		<acme:cancel code="chirp.cancel" url="welcome/index.do"/>
	</jstl:if>
		
</form:form>
