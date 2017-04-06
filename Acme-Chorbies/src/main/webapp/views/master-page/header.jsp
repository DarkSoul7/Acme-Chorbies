<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src="scripts/functions.js"></script>

<div>
	<img src="images/logo.png" alt="Acme-Chorbies Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMINISTRATOR')">
				<li><a class="fNiv" href="searchTemplate/listCachedTime.do"><spring:message code="master.page.cachedTime.list" /></a></li>
				<li><a class="fNiv"><spring:message	code="master.page.administrator.dashboard" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/dashboardC.do"><spring:message code="master.page.administrator.dashboradC" /></a></li>
					<li><a href="administrator/dashboardB.do"><spring:message code="master.page.administrator.dashboradB" /></a></li>
					<li><a href="administrator/dashboardA.do"><spring:message code="master.page.administrator.dashboradA" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CHORBI')">
			<li><a class="fNiv"><spring:message	code="master.page.chorbi" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/action-1.do"><spring:message code="master.page.customer.action.1" /></a></li>
					<li><a href="customer/action-2.do"><spring:message code="master.page.customer.action.2" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li><a href="chorbi/list.do"><spring:message code="master.page.list.chorbi" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.messagingSystem" /></a>
				<ul>
				
					<li class="arrow"></li>
					<li><a href="chirp/send.do"><spring:message code="master.page.messagingSystem.send" /></a></li>
					<li><a href="chirp/sentChirps.do"><spring:message code="master.page.messagingSystem.sentChirps" /></a></li>
					<li><a href="chirp/receivedChirps.do"><spring:message code="master.page.messagingSystem.receivedChirps" /></a></li>
				</ul>
			</li>
			
			<li><a class="fNiv"><spring:message code="master.page.profile" />(<security:authentication property="principal.username" />)</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>


<jstl:if test="${cookie.language.value == null || (cookie.language.value != 'en' && cookie.language.value != 'es')}">
	<script type="text/javascript">window.location.replace('welcome/index.do?language=en');</script>
</jstl:if>
