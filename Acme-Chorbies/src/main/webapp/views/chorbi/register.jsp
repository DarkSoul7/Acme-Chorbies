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

<form:form action="${requestURI}" modelAttribute="chorbiForm">

	<form:hidden path="id"/>
	
	<fieldset>
	<legend><spring:message code="chorbi.personalDetails"/></legend>
	
	<acme:textbox code="chorbi.name" path="name" />
	<br/>
	
	<acme:textbox code="chorbi.surname" path="surname" />
	<br/>
	
	<acme:textbox code="chorbi.phone" path="phone" />
	<br/>
	
	<acme:textbox code="chorbi.email" path="email" />
	<br/>
	
	<acme:textbox code="chorbi.picture" path="picture" />
	<br/>
	
	<acme:textbox code="chorbi.birthDate" path="birthDate" />
	<br/>
	
	<acme:select2 items="${genres}" itemLabel="name" code="chorbi.genre" path="genre"/>
	<br/>
	
	<acme:select2 items="${relationships}" itemLabel="name" code="chorbi.relationship" path="relationShip"/>
	<br/>
	
	<acme:textarea code="chorbi.description" path="description" />
	<br/>
	
	
	</fieldset>
	
	<fieldset>
	<legend><spring:message code="chorbi.coordinates"/></legend>
	
	<acme:textbox code="coordinates.country" path="coordinates.country" />
	<br/>
	
	<acme:textbox code="coordinates.city" path="coordinates.city" />
	<br/>
	
	<acme:textbox code="coordinates.province" path="coordinates.province" />
	<br/>
	
	<acme:textbox code="coordinates.state" path="coordinates.state" />
	<br/>
	
	</fieldset>	
	
	
	<fieldset>
	<legend><spring:message code="chorbi.creditCard"/></legend>
	
	<acme:textbox code="creditCard.holderName" path="creditCard.holderName" />
	<br/>
	
	<acme:select2 items="${brands}" itemLabel="name" code="creditCard.brandName" path="creditCard.brandName"/>
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
	
	<acme:submit code="chorbi.create" name="save"/>
	
	<acme:cancel code="chorbi.cancel" url="welcome/index.do"/>
		
</form:form>
