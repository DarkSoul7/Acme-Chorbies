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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Global variables -->
<spring:message code="dashboard.years" var="years" />

<fieldset>
	<legend>
		<b><spring:message code="dashboard.chorbiesPerCountry" /></b>
	</legend>

	<jstl:set var="i" value="0"></jstl:set>
	<display:table name="chorbiesPerCountry" uid="chorbiesPerCountry"
		requestURI="administrator/dashboardC" class="displaytag" pagesize="5">
		<spring:message code="dashboard.chorbies" var="chorbies" />
		<display:column title="${chorbies}">
			<jstl:out value="${chorbiesPerCountry[0]}"></jstl:out>
		</display:column>

		<spring:message code="dashboard.country" var="country" />
		<display:column title="${country}">
			<jstl:out value="${chorbiesPerCountry[1]}"></jstl:out>
		</display:column>
	</display:table>
</fieldset>

<br />

<fieldset>
	<legend>
		<b><spring:message code="dashboard.chorbiesPerCity" /></b>
	</legend>

	<jstl:set var="i" value="0"></jstl:set>
	<display:table name="chorbiesPerCity" uid="chorbiesPerCity"
		requestURI="administrator/dashboardC" class="displaytag" pagesize="5">
		<spring:message code="dashboard.chorbies" var="chorbies" />
		<display:column title="${chorbies}">
			<jstl:out value="${chorbiesPerCity[0]}"></jstl:out>
		</display:column>

		<spring:message code="dashboard.city" var="city" />
		<display:column title="${city}">
			<jstl:out value="${chorbiesPerCity[1]}"></jstl:out>
		</display:column>
	</display:table>
</fieldset>

<br />

<fieldset>
	<legend>
		<b><spring:message code="dashboard.stadisticsChoribiesAge" /></b>
	</legend>

	<jstl:set var="i" value="0"></jstl:set>
	<display:table name="stadisticsChoribiesAge" id="row"
		requestURI="administrator/dashboardC" class="displaytag">
		<spring:message code="dashboard.statistics" var="statistics" />
		<display:column title="${statistics}">
			<jstl:choose>
				<jstl:when test="${i == 0}">
					<spring:message code="dashboard.min" var="title" />
					<jstl:set var="i" value="1"></jstl:set>
				</jstl:when>
				<jstl:when test="${i == 1}">
					<spring:message code="dashboard.max" var="title" />
					<jstl:set var="i" value="2"></jstl:set>
				</jstl:when>
				<jstl:otherwise>
					<spring:message code="dashboard.avg" var="title" />
					<jstl:set var="i" value="3"></jstl:set>
				</jstl:otherwise>
			</jstl:choose>

			<fmt:formatNumber var="value" value="${row}" maxFractionDigits="2"></fmt:formatNumber>
			<b><jstl:out value="${title}:"></jstl:out></b>
			<jstl:out value="${value} ${years}"></jstl:out>
		</display:column>
	</display:table>
</fieldset>

<br>

<fieldset>
	<legend>
		<b><spring:message code="dashboard.ratioChorbiesWithoutCreditCard" /></b>
	</legend>
	<display:table name="ratioChorbiesWithoutCreditCard" id="row"
		requestURI="administrator/dashboardC" class="displaytag">
		<spring:message code="dashboard.ratio" var="ratio" />
		<display:column title="${ratio}">
			<jstl:out value="${row}%"></jstl:out>
		</display:column>
	</display:table>
</fieldset>

<br>

<fieldset>
	<legend>
		<b><spring:message code="dashboard.ratioChorbiesLooking" /></b>
	</legend>

	<jstl:set var="i" value="0"></jstl:set>
	<display:table name="ratioChorbiesLooking" id="row"
		requestURI="administrator/dashboardC" class="displaytag">
		<spring:message code="dashboard.statistics" var="statistics" />
		<display:column title="${statistics}">
			<jstl:choose>
				<jstl:when test="${i == 0}">
					<spring:message code="dashboard.lookingForActivity" var="title" />
					<jstl:set var="i" value="1"></jstl:set>
				</jstl:when>
				<jstl:when test="${i == 1}">
					<spring:message code="dashboard.lookingForFriends" var="title" />
					<jstl:set var="i" value="2"></jstl:set>
				</jstl:when>
				<jstl:otherwise>
					<spring:message code="dashboard.lookingForLove" var="title" />
					<jstl:set var="i" value="3"></jstl:set>
				</jstl:otherwise>
			</jstl:choose>

			<fmt:formatNumber var="value" value="${row}" maxFractionDigits="2"></fmt:formatNumber>
			<b><jstl:out value="${title}:"></jstl:out></b>
			<jstl:out value="${value}%"></jstl:out>
		</display:column>
	</display:table>
</fieldset>

<acme:cancel url="" code="dasboard.back"/>