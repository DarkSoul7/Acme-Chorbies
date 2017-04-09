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

<form:form action="${RequestURI}" modelAttribute="searchTemplate">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="cachedMoment" />
	<form:hidden path="chorbi" />
	
	<jstl:if test="${pageContext.response.locale.language=='en'}">
		<jstl:set var="itemLabel" value="name"></jstl:set>
	</jstl:if>

	<jstl:if test="${pageContext.response.locale.language=='es'}">
		<jstl:set var="itemLabel" value="spanishName"></jstl:set>
	</jstl:if>
	
	<fieldset>
		<legend><spring:message code="searchTemplate.form"/></legend>
		
		<acme:select2 items="${relationships}" itemLabel="${itemLabel}" code="searchTemplate.relationship" path="relationship"/>
		<br/>
		
		<acme:textbox code="searchTemplate.age" path="age" />
		<br/>
		
		<acme:select2 items="${genres}" itemLabel="${itemLabel}" code="searchTemplate.genre" path="genre"/>
		<br/>
		
		<acme:textbox code="searchTemplate.keyWord" path="keyword" />
		<br/>
	</fieldset>
	
	<div style="padding-left:1cm">
		<fieldset>
			<legend><spring:message code="searchTemplate.coordinates"/></legend>
			
			<acme:textbox code="coordinates.country" path="coordinates.country" />
			<br/>
			
			<acme:textbox code="coordinates.state" path="coordinates.state" />
			<br/>
			
			<acme:textbox code="coordinates.province" path="coordinates.province" />
			<br/>
			
			<acme:textbox code="coordinates.city" path="coordinates.city" />
		</fieldset>	
	</div>
	
	<br/>
	
	<acme:submit code="searchTemplate.save" name="save"/>
	<acme:cancel url="" code="searchTemplate.back"/>
</form:form>

<br/>

<jstl:if test="${errorMessage != null}">
	<spring:message code="searchTemplate.save.error" var="error" />
	<p><font size="3" color="red"><jstl:out value="${error}"></jstl:out></font></p>
</jstl:if>

<h2><spring:message code="searchTemplate.results" /></h2>

<display:table name="cachedChorbies" id="row" requestURI="${requestURI}" pagesize="5">
	
	<spring:message code="chorbi.picture" var="picture" />
	<display:column title="${picture}">
		<img src="${row.picture}" alt="picture" width="70" height="70">
	</display:column>

	<spring:message code="chorbi.name" var="name" />
	<display:column property="name" title="${name}" />
	
	<spring:message code="chorbi.surname" var="surname" />
	<display:column property="surname" title="${surname}" />

	<spring:message code="chorbi.birthDate" var="birthDate" />
	<display:column title="${birthDate}" >
		<jstl:choose>
			<jstl:when test="${pageContext.response.locale.language=='en'}">
				<fmt:formatDate value="${row.birthDate}" pattern="MM/dd/yyyy" />
			</jstl:when>
			<jstl:otherwise>
				<fmt:formatDate value="${row.birthDate}" pattern="dd/MM/yyyy" />
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	<spring:message code="chorbi.genre" var="genre" />
	<display:column title="${genre}">
		<jstl:if test="${pageContext.response.locale.language=='en'}">
			<jstl:out value="${row.genre.name}"></jstl:out>
		</jstl:if>
		
		<jstl:if test="${pageContext.response.locale.language=='es'}">
			<jstl:out value="${row.genre.spanishName}"></jstl:out>
		</jstl:if>
	</display:column>
	
	<spring:message code="chorbi.relationship" var="relationship" />
	<display:column title="${relationship}">
		<jstl:if test="${pageContext.response.locale.language=='en'}">
			<jstl:out value="${row.relationship.name}"></jstl:out>
		</jstl:if>
		
		<jstl:if test="${pageContext.response.locale.language=='es'}">
			<jstl:out value="${row.relationship.spanishName}"></jstl:out>
		</jstl:if>
	</display:column>
</display:table>