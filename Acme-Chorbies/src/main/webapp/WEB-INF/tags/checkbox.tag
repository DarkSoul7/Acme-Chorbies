<%--
 * textbox.tag
 *
 * Copyright (C) 2014 Universidad de Sevilla
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

<%@ attribute name="readonly" required="false" %>
<%@ attribute name="varConditions" required="false" %>
<%@ attribute name="mandatory" required="false" %>

<jstl:if test="${readonly == null}">
	<jstl:set var="readonly" value="false" />
</jstl:if>

<jstl:if test="${varConditions == null}">
	<jstl:set var="varConditions" value="false" />
</jstl:if>

<jstl:if test="${mandatory == null}">
	<jstl:set var="mandatory" value="false" />
</jstl:if>

<%-- Definition --%>

<spring:bind path="${path}">
	<div style="padding-left:1cm">
		<div class="form-group">
			<div style="float:left">
				<form:label path="${path}" cssClass="${status.error? 'error':''}">
					<spring:message code="${code}" />
					<jstl:if test="${mandatory == true}">
						<a class="error">(*)</a>
					</jstl:if>
				</form:label>
			</div>
			<div>
				&nbsp;&nbsp;<form:checkbox path="${path}" />
			</div>
		</div>
		<form:errors path="${path}" cssClass="error" />
	</div>
</spring:bind>