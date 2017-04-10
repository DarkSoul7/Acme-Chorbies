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

<display:table name="chirps" id="row" requestURI="${requestURI}" pagesize="5">

	<spring:message code="chirp.subject" var="subject" />
	<display:column property="subject" title="${subject}" />
	
	<spring:message code="chirp.moment" var="description" />
	<display:column title="${description}" >
		<jstl:choose>
			<jstl:when test="${cookie.language.value == 'en'}">
				<fmt:formatDate value="${row.moment}" pattern="MM/dd/yyyy HH:mm" />
			</jstl:when>
			<jstl:otherwise>
				<fmt:formatDate value="${row.moment}" pattern="dd/MM/yyyy HH:mm" />
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	<spring:message code="chirp.sender" var="sender" />
	<display:column title="${sender}">
		<jstl:out value="${row.sender.name} ${row.sender.surname} (${row.sender.userAccount.username})" />
	</display:column>
	
	<spring:message code="chirp.receiver" var="receiver" />
	<display:column title="${receiver}">
		<jstl:out value="${row.receiver.name} ${row.receiver.surname} (${row.receiver.userAccount.username})" />
	</display:column>
	
	<display:column>
		<acme:cancel code="chirp.showDetails" url="chirp/showChirp.do?chirpId=${row.id}" />
	</display:column>
	
	<display:column>
		<jstl:if test="${row.childChirp == null}">
			<acme:confirm code="chirp.delete" url="chirp/delete.do?chirpId=${row.id}&url=${requestURI}" msg="chirp.confirmDeletion" />
		</jstl:if>
	</display:column>
	
</display:table>