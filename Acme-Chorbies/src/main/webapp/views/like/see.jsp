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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<b><spring:message code="like.receiver" />: </b>
<jstl:out value="${like.receiver.name} ${like.receiver.surname}"></jstl:out>
<br/>
<b><spring:message code="like.comment" />: </b>
<jstl:out value="${like.comment}"></jstl:out>
<br/>
<b><spring:message code="like.moment" />: </b>
<jstl:choose>
	<jstl:when test="${pageContext.response.locale.language=='en'}">
		<fmt:formatDate value="${like.moment}" pattern="MM/dd/yyyy HH:mm" />
	</jstl:when>
	<jstl:otherwise>
		<fmt:formatDate value="${like.moment}" pattern="dd/MM/yyyy HH:mm" />
	</jstl:otherwise>
</jstl:choose>

<br/>
<br/>
<acme:confirm url="like/cancel.do?chorbiId=${like.receiver.id}" code="like.cancelLike" msg="like.cancelLikeConfirm" />
<acme:cancel url="chorbi/showDetails.do?chorbiId=${like.receiver.id}" code="like.back" />