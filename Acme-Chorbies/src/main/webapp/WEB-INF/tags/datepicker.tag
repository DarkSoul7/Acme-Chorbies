<%--
 * textbox.tag
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

<script src="scripts/bootstrap-datepicker.js"></script>
<script src="scripts/bootstrap-datepicker.es.min.js"></script>

<link rel="stylesheet" type="text/css" href="styles/datepicker-standalone.css">

<%-- Attributes --%> 
 
<%@ attribute name="path" required="true" %>
<%@ attribute name="code" required="true" %>

<%@ attribute name="disabled" required="false" %>
<%@ attribute name="mandatory" required="false" %>

<jstl:if test="${disabled == null}">
	<jstl:set var="disabled" value="false" />
</jstl:if>

<jstl:if test="${mandatory == null}">
	<jstl:set var="mandatory" value="false" />
</jstl:if>

<%-- Definition --%>

<spring:bind path="${path}">
	<div class="form-group ${status.error? 'has-error':''}" style="padding-left:1cm">
		<form:label path="${path}">
			<spring:message code="${code}" />:
			<jstl:if test="${mandatory == true}">
				<a class="error">(*)</a>
			</jstl:if>
		</form:label>
		<div class="input-group date" data-provide="datepicker" id="${path}_datepicker">
			<form:input path="${path}" disabled="${disabled}" class="form-control" />	
		    <div class="input-group-addon">
		        <span class="glyphicon glyphicon-calendar"></span>
		    </div>
		</div>
		<form:errors path="${path}" cssClass="error" />
		<script type="text/javascript">
            $(function () {
            	$.fn.datepicker.defaults.language = '${pageContext.response.locale.language}';
            	$.fn.datepicker.defaults.format = 'dd/mm/yyyy';
                $('#${path}_datepicker').datepicker({
                    autoclose: true,
                });
            });
        </script>
	</div>
</spring:bind>
