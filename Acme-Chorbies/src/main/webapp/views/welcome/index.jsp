<%--
 * index.jsp
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

<jstl:if test="${fullName == null}">
	<jstl:set var="fullName"><spring:message code="welcome.anonymousUser" /></jstl:set>
</jstl:if>

<p><spring:message code="welcome.greeting.prefix" /> ${fullName}<spring:message code="welcome.greeting.suffix" /></p>

<p><spring:message code="welcome.welcome" /></p>

<br/>

<div>
	<spring:message code="welcome.advertisement" var="advertisement" />
	<p style="font-weight:bold; font-style:italic;">
		<jstl:out value="${advertisement}" />
		<br/>
		<img src="${banner.picture}" alt="${advertisement}" height="200" width="300" />
	</p>
</div>