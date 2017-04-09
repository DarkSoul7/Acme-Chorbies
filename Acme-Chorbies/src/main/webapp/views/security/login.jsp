 <%--
 * login.jsp
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

<form:form action="j_spring_security_check" modelAttribute="credentials">

	<acme:textbox code="security.username" path="username"/>
	<br />

	<acme:password code="security.password" path="password"/>
	<br />
	
	<jstl:if test="${showError == true}">
		<div class="error">
			<jstl:if test="${SPRING_SECURITY_LAST_EXCEPTION.message.equals('User is disabled')}">
				<spring:message code="security.login.disabled" />
				<br />
				<spring:message code="security.login.disabled2" />
			</jstl:if>
			
			<jstl:if test="${SPRING_SECURITY_LAST_EXCEPTION.message.equals('Bad credentials')}">
				<spring:message code="security.login.failed" />
			</jstl:if>
		</div>
	</jstl:if>
	
	<jstl:if test="${showError == true}">
		<div class="error">
			<spring:message code="security.login.failed" />
		</div>
	</jstl:if>
	
	<div style="padding-left:1cm">
		<acme:submit name="" code="security.login"/>
	</div>
	
</form:form>