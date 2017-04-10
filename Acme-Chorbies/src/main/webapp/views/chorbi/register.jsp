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

<jstl:if test="${pageContext.response.locale.language=='en'}">
	<jstl:set var="itemLabel" value="name"></jstl:set>
</jstl:if>

<jstl:if test="${pageContext.response.locale.language=='es'}">
	<jstl:set var="itemLabel" value="spanishName"></jstl:set>
</jstl:if>

<jstl:if test="${successMessage != null}">
	<div class="alert alert-success"><spring:message code="${successMessage}" /></div>
</jstl:if>

<form:form action="${requestURI}" modelAttribute="chorbiForm">
	<form:hidden path="id"/>
	
	<jstl:if test="${chorbiForm.id != 0}">
		<form:hidden path="acceptCondition"/>
		
		<spring:message code="chorbi.picture" var="picture" />
		<img src="${pictureUrl}" alt="picture" width="70" height="70">
	</jstl:if>
	
	<fieldset>
	<legend><spring:message code="chorbi.personalDetails"/></legend>
	
	<acme:textbox code="chorbi.name" path="name" mandatory="true" />
	<br/>
	
	<acme:textbox code="chorbi.surname" path="surname" mandatory="true" />
	<br/>
	
	<acme:textbox code="chorbi.phone" path="phone" mandatory="true" />
	<br/>
	
	<acme:textbox code="chorbi.email" path="email" mandatory="true" />
	<br/>
	
	<acme:textbox code="chorbi.picture" path="picture" mandatory="true" />
	<br/>
	
	<acme:datepicker code="chorbi.birthDate" path="birthDate" mandatory="true" />
	<acme:error path="overAge"/>
	<br/>
	
	<acme:select2 items="${genres}" itemLabel="${itemLabel}" code="chorbi.genre" path="genre" mandatory="true"/>
	<br/>
	
	<acme:select2 items="${relationships}" itemLabel="${itemLabel}" code="chorbi.relationship" path="relationship" mandatory="true"/>
	<br/>
	
	<acme:textarea code="chorbi.description" path="description" mandatory="true" />
	<br/>
	</fieldset>
	<br/>
	<fieldset>
	<legend><spring:message code="chorbi.coordinates"/></legend>
	
	<acme:textbox code="coordinates.country" path="coordinates.country" mandatory="true" />
	<br/>
	
	<acme:textbox code="coordinates.state" path="coordinates.state" />
	<br/>
	
	<acme:textbox code="coordinates.province" path="coordinates.province" />
	<br/>
	
	<acme:textbox code="coordinates.city" path="coordinates.city" mandatory="true" />
	<br/>
	
	</fieldset>	
	
	<br/>
	
	<fieldset>
	<legend><spring:message code="chorbi.creditCard"/></legend>
	
	<acme:textbox code="creditCard.holderName" path="creditCard.holderName" />
	<br/>
	
	<acme:select2 items="${brands}" itemLabel="${itemLabel}" code="creditCard.brandName" path="creditCard.brandName"/>
	<br/>
	
	<acme:textbox code="creditCard.number" path="creditCard.number" />
	<br/>
	
	<acme:textbox code="creditCard.expirationMonth" path="creditCard.expirationMonth" />
	<br/>
	
	<acme:textbox code="creditCard.expirationYear" path="creditCard.expirationYear" />
	<br/>
	
	<acme:textbox code="creditCard.cvv" path="creditCard.cvv" />
	<br/>
	</fieldset>	
	
	<security:authorize access="isAnonymous()" >
		<br/>
		<fieldset>
			<legend><spring:message code="chorbi.userAccountDetails"/></legend>
				<br/>
			<acme:textbox code="chorbi.username" path="userAccount.username" mandatory="true" />
				<br/>
			<acme:password code="chorbi.password" path="userAccount.password" mandatory="true" />
				<br/>
			<acme:password code="chorbi.repeatPassword" path="repeatPassword" mandatory="true" />
				<br>
			<acme:checkbox code="chorbi.acceptCondition" path="acceptCondition" mandatory="true"/>
		</fieldset>
	</security:authorize>
	
	<br/>
	<br/>
	
	<jstl:if test="${chorbiForm.id != 0}">
		<acme:submit code="chorbi.save" name="save"/>
		<acme:cancel code="chorbi.back" url=""/>
	</jstl:if>
	<jstl:if test="${chorbiForm.id == 0}">
		<acme:submit code="chorbi.create" name="save"/>
		<acme:cancel code="chorbi.cancel" url=""/>
	</jstl:if>
		
</form:form>
