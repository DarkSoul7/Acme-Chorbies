<%--
 * select.tag
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty" %>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%> 

<%@ attribute name="path" required="true" %>
<%@ attribute name="code" required="true" %>
<%@ attribute name="items" required="true" type="java.util.Collection" %>
<%@ attribute name="itemLabel" required="true" %>

<%@ attribute name="id" required="false" %>
<%@ attribute name="onchange" required="false" %>
<%@ attribute name="mandatory" required="false" %>

<jstl:if test="${id == null}">
	<jstl:set var="id" value="${UUID.randomUUID().toString()}" />
</jstl:if>

<jstl:if test="${onchange == null}">
	<jstl:set var="onchange" value="javascript: return true;" />
</jstl:if>

<jstl:if test="${mandatory == null}">
	<jstl:set var="mandatory" value="false" />
</jstl:if>

<%-- Definition --%>
<spring:bind path="${path}">
	<div class="form-group  ${status.error? 'has-error':''}" style="padding-left:1cm">
		<form:label path="${path}">
			<spring:message code="${code}" />:
			<jstl:if test="${mandatory == true}">
				<a class="error">(*)</a>
			</jstl:if>
		</form:label>	
		<form:select id="${id}" path="${path}" onchange="${onchange}" class="form-control">
			<form:option value="0" label="----" />		
			<form:options items="${items}" itemValue="id" itemLabel="${itemLabel}" />
		</form:select>
		<form:errors path="${path}" cssClass="error" />
	</div>
</spring:bind>

