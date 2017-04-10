<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:choose>
	<jstl:when test="${cookie.language.value == 'en'}">
		<fmt:formatDate value="${chirp.moment}" pattern="MM/dd/yyyy HH:mm" var="moment" />
	</jstl:when>
	<jstl:otherwise>
		<fmt:formatDate value="${chirp.moment}" pattern="dd/MM/yyyy HH:mm" var="moment" />
	</jstl:otherwise>
</jstl:choose>

<h2><jstl:out value="${chirp.subject}"></jstl:out></h2>
<fieldset>

	<b><spring:message code="chirp.sender" />:</b>
	<jstl:out value="${chirp.sender.name} ${chirp.sender.surname}"></jstl:out>
	<br/><br/>
	
	<b><spring:message code="chirp.receiver" />:</b>
	<jstl:out value="${chirp.receiver.name} ${chirp.receiver.surname}"></jstl:out>
	<br/><br/>
	
	<jstl:out value="${moment}"></jstl:out>
	<br/><br/>
	
	<jstl:if test="${chirp.attachments != ''}">
		<b><spring:message code="chirp.attachments" /></b>
		<jstl:forEach items="${fn:split(chirp.attachments, ',')}" var="attachment">
			<a href="${attachment}">${attachment}</a>
			<br/>
		</jstl:forEach>
	</jstl:if>
	
</fieldset>

<br/>
<textarea disabled="disabled" style="width:50vw; height:20vh; color:black; background-color:white;"><jstl:out value="${chirp.text}"></jstl:out></textarea>
<br/>

<jstl:if test="${chirp != null}">
	<acme:cancel url="chirp/reply.do?chirpId=${chirp.id}" code="chirp.reply" class_="btn btn-primary" />
	<acme:cancel url="chirp/forward.do?chirpId=${chirp.id}" code="chirp.forward" class_="btn btn-primary" />	
	
	<jstl:if test="${chirp.parentChirp != null}">
		<acme:cancel url="chirp/showChirp.do?chirpId=${chirp.parentChirp.id}" code="chirp.parentChirp" class_="btn btn-primary" />
	</jstl:if>
	
	<jstl:if test="${chirp.childChirp != null}">
		<acme:cancel url="chirp/showChirp.do?chirpId=${chirp.childChirp.id}" code="chirp.childChirp" class_="btn btn-primary" />
	</jstl:if>
	
	<jstl:if test="${chirp.childChirp == null}">
		<acme:confirm code="chirp.delete" url="chirp/delete.do?chirpId=${chirp.id}&url=" msg="chirp.confirmDeletion" />
	</jstl:if>
	
	<acme:cancel url="" code="chirp.back"/>	
</jstl:if>