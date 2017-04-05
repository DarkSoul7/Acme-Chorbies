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

<display:table name="cachedTime" id="row" requestURI="${RequestURI}" pagesize="5">

	<spring:message code="cachedTime.cachedValue" var="cachedValue" />
	<display:column property="cachedValue" title="${cachedValue}" />

	<display:column>
		<acme:cancel url="searchTemplate/editCachedTime.do?" code="cachedTime.edit"/>
	</display:column>
</display:table>