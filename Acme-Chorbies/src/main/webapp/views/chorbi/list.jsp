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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="chorbies" id="row" requestURI="${requestURI}" pagesize="5">
	
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
	
	<display:column>
		<jstl:if test="${listForm == true}">
			<acme:cancel url="chorbi/showDetails.do?chorbiId=${row.id}" code="chorbi.showDetails"/>
		</jstl:if>
		<jstl:if test="${listForm == false}">
			<acme:cancel url="administrator/chorbi/showDetails.do?chorbiId=${row.id}" code="chorbi.showDetails"/>
		</jstl:if>
	</display:column>
	
	<display:column>
		<jstl:if test="${listForm == true}">
			<acme:cancel url="chorbi/receivedLikesAuthors.do?authorId=${row.id}" code="chorbi.receivedLikes"/>
		</jstl:if>
		<jstl:if test="${listForm == false}">
			<acme:cancel url="administrator/chorbi/receivedLikesAuthors.do?authorId=${row.id}" code="chorbi.receivedLikes"/>
		</jstl:if>
	</display:column>
	
	<display:column>
		<jstl:if test="${listForm == true}">
			<acme:cancel url="chorbi/givenLikesReceivers.do?receiverId=${row.id}" code="chorbi.givenLikes"/>
		</jstl:if>
		<jstl:if test="${listForm == false}">
			<acme:cancel url="administrator/chorbi/givenLikesReceivers.do?receiverId=${row.id}" code="chorbi.givenLikes"/>
		</jstl:if>
	</display:column>
	
	<security:authorize access="hasRole('CHORBI')">
		<jstl:if test="${listForm == true}">
			<display:column>
				<jstl:if test="${row.id !=  principalId}">
					<jstl:if test="${row.liked == false}">
						<acme:cancel url="like/register.do?chorbiId=${row.id}" code="chorbi.sendLike"/>
					</jstl:if>
					<jstl:if test="${row.liked == true}">
						<acme:cancel url="like/see.do?chorbiId=${row.id}" code="chorbi.seeLike"/>
					</jstl:if>
				</jstl:if>
			</display:column>
		</jstl:if>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMINISTRATOR')">
		<display:column>
			<jstl:if test="${row.userAccount.enabled == true }">
				<acme:confirm url="administrator/ban.do?chorbiId=${row.id}" code="chorbi.ban" msg="chorbi.banConfirm" />
			</jstl:if>
			<jstl:if test="${row.userAccount.enabled == false }">
				<acme:confirm url="administrator/unBan.do?chorbiId=${row.id}" code="chorbi.unBan" msg="chorbi.unBanConfirm" />
			</jstl:if>
			</display:column>
	</security:authorize>	
</display:table>
