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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<display:table name="chorbies" id="row" requestURI="${RequestURI}"
	pagesize="5">
	
	<spring:message code="chorbi.picture" var="picture" />
	<display:column title="${picture}">
		<img src="${row.picture}" alt="picture" width="70" height="70">
	</display:column>

	<spring:message code="chorbi.name" var="name" />
	<display:column property="name" title="${name}" />
	
	<spring:message code="chorbi.surname" var="surname" />
	<display:column property="surname" title="${surname}" />

	<spring:message code="chorbi.phone" var="phone" />
	<display:column property="phone" title="${phone}" />
	
	<spring:message code="chorbi.email" var="email" />
	<display:column property="email" title="${email}" />
	
	<spring:message code="chorbi.birthDate" var="birthDate" />
	<display:column property="birthDate" title="${birthDate}" />
	
	<spring:message code="chorbi.relationship" var="relationship" />
	<display:column title="${relationship}">
		
		<jstl:if test="${cookie['language'].value.equals('en')}">
			<jstl:out value="${row.relationship.name}"></jstl:out>
		</jstl:if>
		
		<jstl:if test="${cookie['language'].value.equals('es')}">
			<jstl:out value="${row.relationship.spanishName}"></jstl:out>
		</jstl:if>
		
		<jstl:if test="${cookie['language'].value== null}">
			<jstl:out value="${row.relationship.name}"></jstl:out>
		</jstl:if>
		
	</display:column>
	
	<spring:message code="chorbi.genre" var="genre" />
	<display:column title="${genre}">
		
		<jstl:if test="${cookie['language'].value.equals('en')}">
			<jstl:out value="${row.genre.name}"></jstl:out>
		</jstl:if>
		
		<jstl:if test="${cookie['language'].value.equals('es')}">
			<jstl:out value="${row.genre.spanishName}"></jstl:out>
		</jstl:if>
		
		<jstl:if test="${cookie['language'].value== null}">
			<jstl:out value="${row.genre.name}"></jstl:out>
		</jstl:if>
		
	</display:column>
	
	<spring:message code="chorbi.coordinates.city" var="coordinatesCity" />
	<display:column property="coordinates.city" title="${coordinatesCity}" />
	
	<spring:message code="chorbi.coordinates.state" var="coordinatesState" />
	<display:column property="coordinates.state" title="${coordinatesState}" />
	
	<spring:message code="chorbi.coordinates.province" var="coordinatesProvince" />
	<display:column property="coordinates.province" title="${coordinatesProvince}" />
	
	<spring:message code="chorbi.coordinates.country" var="coordinatesCountry" />
	<display:column property="coordinates.country" title="${coordinatesCountry}" />
	

	
	


</display:table>
