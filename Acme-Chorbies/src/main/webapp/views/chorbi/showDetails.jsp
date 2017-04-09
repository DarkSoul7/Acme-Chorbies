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

<!-- Variables -->

<jstl:choose>
	<jstl:when test="${pageContext.response.locale.language=='en'}">
		<jstl:set var="pattern" value="MM/dd/yyyy" />
		<fmt:formatDate var="birthDate" value="${chorbi.birthDate}" pattern="${pattern}" />
		<jstl:set var="genre" value="${chorbi.genre.name}" />
		<jstl:set var="relationship" value="${chorbi.relationship.name}" />
	</jstl:when>
	<jstl:otherwise>
		<jstl:set var="pattern" value="dd/MM/yyyy" />
		<fmt:formatDate var="birthDate" value="${chorbi.birthDate}" pattern="${pattern}" />
		<jstl:set var="genre" value="${chorbi.genre.spanishName}" />
		<jstl:set var="relationship" value="${chorbi.relationship.spanishName}" />
	</jstl:otherwise>
</jstl:choose>

<jstl:set var="coordinates" value="${chorbi.coordinates.city}" />
<jstl:if test="${chorbi.coordinates.province != ''}">
	<jstl:set var="coordinates" value="${coordinates}, ${chorbi.coordinates.province}" />
</jstl:if>
<jstl:set var="coordinates" value="${coordinates} (" />
<jstl:if test="${chorbi.coordinates.state != ''}">
	<jstl:set var="coordinates" value="${coordinates}${chorbi.coordinates.state}, " />
</jstl:if>
<jstl:set var="coordinates" value="${coordinates}${chorbi.coordinates.country})" />

<!-- HTML -->

<div style="float:left; padding-right:0.5cm">
	<img src="${chorbi.picture}" alt="Picture" width="160" height="160">
</div>

<div>
	<b><spring:message code="chorbi.name" />: </b><jstl:out value="${chorbi.name}" />
	<br/>
	<b><spring:message code="chorbi.surname" />: </b><jstl:out value="${chorbi.surname}" />
	<br/>
	<b><spring:message code="chorbi.birthDate" />: </b><jstl:out value="${birthDate}" /> (<jstl:out value="${pattern}" />)
	<br/>
	<b><spring:message code="chorbi.genre" />: </b><jstl:out value="${genre}" />
	<br/>
	<b><spring:message code="chorbi.relationship" />:</b> <jstl:out value="${relationship}" />
	<br/>
	<b><spring:message code="chorbi.email" />: </b><jstl:out value="${chorbi.email}" />
	<br/>
	<b><spring:message code="chorbi.phone" />: </b><jstl:out value="${chorbi.phone}" />
	<br/>
	<b><spring:message code="chorbi.coordinates" />: </b><jstl:out value="${coordinates}" />
</div>

<div style="clear:both">
	<br/>
	<b><spring:message code="chorbi.description" /></b>
	<br/>
	<textarea rows="5" cols="100" disabled="disabled" style="background-color:white">${chorbi.description}</textarea>
</div>

<div style="clear:both">
	<br/>
	<b><spring:message code="chorbi.chorbiesFromReceiver" /></b>
	<display:table name="chorbiesFromReceiver" requestURI="${requestURI}" pagesize="5" uid="chorbiesFromReceiverTable">
		<spring:message code="chorbi.picture" var="picture" />
		<display:column title="${picture}">
			<img src="${chorbiesFromReceiverTable.picture}" alt="picture" width="70" height="70">
		</display:column>
	
		<spring:message code="chorbi.name" var="name" />
		<display:column property="name" title="${name}" />
		
		<spring:message code="chorbi.surname" var="surname" />
		<display:column property="surname" title="${surname}" />
	
		<spring:message code="chorbi.birthDate" var="birthDate" />
		<display:column title="${birthDate}" >
			<jstl:choose>
				<jstl:when test="${pageContext.response.locale.language=='en'}">
					<fmt:formatDate value="${chorbiesFromReceiverTable.birthDate}" pattern="MM/dd/yyyy" />
				</jstl:when>
				<jstl:otherwise>
					<fmt:formatDate value="${chorbiesFromReceiverTable.birthDate}" pattern="dd/MM/yyyy" />
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
		
		<spring:message code="chorbi.genre" var="genre" />
		<display:column title="${genre}">
			<jstl:if test="${pageContext.response.locale.language=='en'}">
				<jstl:out value="${chorbiesFromReceiverTable.genre.name}"></jstl:out>
			</jstl:if>
			
			<jstl:if test="${pageContext.response.locale.language=='es'}">
				<jstl:out value="${chorbiesFromReceiverTable.genre.spanishName}"></jstl:out>
			</jstl:if>
		</display:column>
		
		<spring:message code="chorbi.relationship" var="relationship" />
		<display:column title="${relationship}">
			<jstl:if test="${pageContext.response.locale.language=='en'}">
				<jstl:out value="${chorbiesFromReceiverTable.relationship.name}"></jstl:out>
			</jstl:if>
			
			<jstl:if test="${pageContext.response.locale.language=='es'}">
				<jstl:out value="${chorbiesFromReceiverTable.relationship.spanishName}"></jstl:out>
			</jstl:if>
		</display:column>
		
		<display:column>
			<jstl:if test="${listForm == true}">
				<acme:cancel url="chorbi/showDetails.do?chorbiId=${chorbiesFromReceiverTable.id}" code="chorbi.showDetails"/>
			</jstl:if>
			<jstl:if test="${listForm == false}">
				<acme:cancel url="administrator/chorbi/showDetails.do?chorbiId=${chorbiesFromReceiverTable.id}" code="chorbi.showDetails"/>
			</jstl:if>
		</display:column>
		
		<display:column>
			<jstl:if test="${listForm == true}">
				<acme:cancel url="chorbi/receivedLikesAuthors.do?authorId=${chorbiesFromReceiverTable.id}" code="chorbi.receivedLikes"/>
			</jstl:if>
			<jstl:if test="${listForm == false}">
				<acme:cancel url="administrator/chorbi/receivedLikesAuthors.do?authorId=${chorbiesFromReceiverTable.id}" code="chorbi.receivedLikes"/>
			</jstl:if>
		</display:column>
		
		<display:column>
			<jstl:if test="${listForm == true}">
				<acme:cancel url="chorbi/givenLikesReceivers.do?receiverId=${chorbiesFromReceiverTable.id}" code="chorbi.givenLikes"/>
			</jstl:if>
			<jstl:if test="${listForm == false}">
				<acme:cancel url="administrator/chorbi/givenLikesReceivers.do?receiverId=${chorbiesFromReceiverTable.id}" code="chorbi.givenLikes"/>
			</jstl:if>
		</display:column>
		
		<security:authorize access="hasRole('CHORBI')">
			<jstl:if test="${listForm == true}">
				<display:column>
					<jstl:if test="${chorbiesFromReceiverTable.id !=  principalId}">
						<jstl:if test="${chorbiesFromReceiverTable.liked == false}">
							<acme:cancel url="like/register.do?chorbiId=${chorbiesFromReceiverTable.id}" code="chorbi.sendLike"/>
						</jstl:if>
						<jstl:if test="${chorbiesFromReceiverTable.liked == true}">
							<acme:cancel url="like/see.do?chorbiId=${chorbiesFromReceiverTable.id}" code="chorbi.seeLike"/>
						</jstl:if>
					</jstl:if>
				</display:column>
			</jstl:if>
		</security:authorize>
		
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<display:column>
				<jstl:if test="${chorbiesFromReceiverTable.userAccount.enabled == true }">
					<acme:cancel url="administrator/ban.do?chorbiId=${chorbiesFromReceiverTable.id}" code="chorbi.ban"/>
				</jstl:if>
				<jstl:if test="${chorbiesFromReceiverTable.userAccount.enabled == false }">
					<acme:cancel url="administrator/unBan.do?chorbiId=${chorbiesFromReceiverTable.id}" code="chorbi.unBan"/>
				</jstl:if>
			</display:column>
		</security:authorize>	
	</display:table>
</div>

<div style="clear:both">
	<br/>
	<b><spring:message code="chorbi.chorbiesFromAuthor" /></b>
	<display:table name="chorbiesFromAuthor" requestURI="${requestURI}" pagesize="5" uid="chorbiesFromAuthorTable">
		<spring:message code="chorbi.picture" var="picture" />
		<display:column title="${picture}">
			<img src="${chorbiesFromAuthorTable.picture}" alt="picture" width="70" height="70">
		</display:column>
	
		<spring:message code="chorbi.name" var="name" />
		<display:column property="name" title="${name}" />
		
		<spring:message code="chorbi.surname" var="surname" />
		<display:column property="surname" title="${surname}" />
	
		<spring:message code="chorbi.birthDate" var="birthDate" />
		<display:column title="${birthDate}" >
			<jstl:choose>
				<jstl:when test="${pageContext.response.locale.language=='en'}">
					<fmt:formatDate value="${chorbiesFromAuthorTable.birthDate}" pattern="MM/dd/yyyy" />
				</jstl:when>
				<jstl:otherwise>
					<fmt:formatDate value="${chorbiesFromAuthorTable.birthDate}" pattern="dd/MM/yyyy" />
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
		
		<spring:message code="chorbi.genre" var="genre" />
		<display:column title="${genre}">
			<jstl:if test="${pageContext.response.locale.language=='en'}">
				<jstl:out value="${chorbiesFromAuthorTable.genre.name}"></jstl:out>
			</jstl:if>
			
			<jstl:if test="${pageContext.response.locale.language=='es'}">
				<jstl:out value="${chorbiesFromAuthorTable.genre.spanishName}"></jstl:out>
			</jstl:if>
		</display:column>
		
		<spring:message code="chorbi.relationship" var="relationship" />
		<display:column title="${relationship}">
			<jstl:if test="${pageContext.response.locale.language=='en'}">
				<jstl:out value="${chorbiesFromAuthorTable.relationship.name}"></jstl:out>
			</jstl:if>
			
			<jstl:if test="${pageContext.response.locale.language=='es'}">
				<jstl:out value="${chorbiesFromAuthorTable.relationship.spanishName}"></jstl:out>
			</jstl:if>
		</display:column>
		
		<display:column>
			<jstl:if test="${listForm == true}">
				<acme:cancel url="chorbi/showDetails.do?chorbiId=${chorbiesFromAuthorTable.id}" code="chorbi.showDetails"/>
			</jstl:if>
			<jstl:if test="${listForm == false}">
				<acme:cancel url="administrator/chorbi/showDetails.do?chorbiId=${chorbiesFromAuthorTable.id}" code="chorbi.showDetails"/>
			</jstl:if>
		</display:column>
		
		<display:column>
			<jstl:if test="${listForm == true}">
				<acme:cancel url="chorbi/receivedLikesAuthors.do?authorId=${chorbiesFromAuthorTable.id}" code="chorbi.receivedLikes"/>
			</jstl:if>
			<jstl:if test="${listForm == false}">
				<acme:cancel url="administrator/chorbi/receivedLikesAuthors.do?authorId=${chorbiesFromAuthorTable.id}" code="chorbi.receivedLikes"/>
			</jstl:if>
		</display:column>
		
		<display:column>
			<jstl:if test="${listForm == true}">
				<acme:cancel url="chorbi/givenLikesReceivers.do?receiverId=${chorbiesFromAuthorTable.id}" code="chorbi.givenLikes"/>
			</jstl:if>
			<jstl:if test="${listForm == false}">
				<acme:cancel url="administrator/chorbi/givenLikesReceivers.do?receiverId=${chorbiesFromAuthorTable.id}" code="chorbi.givenLikes"/>
			</jstl:if>
		</display:column>
		
		<security:authorize access="hasRole('CHORBI')">
			<jstl:if test="${listForm == true}">
				<display:column>
					<jstl:if test="${chorbiesFromAuthorTable.id !=  principalId}">
						<jstl:if test="${chorbiesFromAuthorTable.liked == false}">
							<acme:cancel url="like/register.do?chorbiId=${chorbiesFromAuthorTable.id}" code="chorbi.sendLike"/>
						</jstl:if>
						<jstl:if test="${chorbiesFromAuthorTable.liked == true}">
							<acme:cancel url="like/see.do?chorbiId=${chorbiesFromAuthorTable.id}" code="chorbi.seeLike"/>
						</jstl:if>
					</jstl:if>
				</display:column>
			</jstl:if>
		</security:authorize>
		
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<display:column>
				<jstl:if test="${chorbiesFromAuthorTable.userAccount.enabled == true }">
					<acme:cancel url="administrator/ban.do?chorbiId=${chorbiesFromAuthorTable.id}" code="chorbi.ban"/>
				</jstl:if>
				<jstl:if test="${chorbiesFromAuthorTable.userAccount.enabled == false }">
					<acme:cancel url="administrator/unBan.do?chorbiId=${chorbiesFromAuthorTable.id}" code="chorbi.unBan"/>
				</jstl:if>
			</display:column>
		</security:authorize>	
	</display:table>
	
	<div>
		<jstl:if test="${row.id !=  principalId}">
			<jstl:if test="${chorbi.liked == false}">
				<acme:cancel url="like/register.do?chorbiId=${chorbi.id}" code="chorbi.sendLike"/>
			</jstl:if>
			<jstl:if test="${chorbi.liked == true}">
				<acme:cancel url="like/see.do?chorbiId=${chorbi.id}" code="chorbi.seeLike"/>
			</jstl:if>
		</jstl:if>
		
		<jstl:if test="${listForm == true}">
			<acme:cancel url="chorbi/list.do" code="chorbi.back"/>
		</jstl:if>
		<jstl:if test="${listForm == false}">
			<acme:cancel url="administrator/chorbi/list.do" code="chorbi.back"/>
		</jstl:if>
	</div>
</div>