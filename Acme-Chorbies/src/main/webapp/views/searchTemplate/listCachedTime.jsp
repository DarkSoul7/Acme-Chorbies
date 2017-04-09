<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="cachedTime" id="row" requestURI="${RequestURI}" pagesize="5">

	<spring:message code="cachedTime.cachedHour" var="cachedHour" />
	<display:column property="cachedHour" title="${cachedHour}" />
	
	<spring:message code="cachedTime.cachedMinute" var="cachedMinute" />
	<display:column property="cachedMinute" title="${cachedMinute}" />
	
	<spring:message code="cachedTime.cachedSecond" var="cachedSecond" />
	<display:column property="cachedSecond" title="${cachedSecond}" />

	<display:column>
		<acme:cancel url="searchTemplate/editCachedTime.do" code="cachedTime.edit"/>
	</display:column>
</display:table>

<acme:cancel url="" code="searchTemplate.back" />