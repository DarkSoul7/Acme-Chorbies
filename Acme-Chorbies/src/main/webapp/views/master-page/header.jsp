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

<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="/Acme-Chorbies"><img style="max-height: 150%;" src="images/logo-chorbies.jpg" alt="Acme-Chorbies Co., Inc." /></a>
		</div>
		<ul id="jMenu" class="nav navbar-nav" style="margin:0; padding:0">
			<!-- Do not forget the "fNiv" class for the first level links !! -->
			
			<security:authorize access="hasRole('ADMINISTRATOR')">
				<li><a class="fNiv" href="banner/list.do"><spring:message code="master.page.banner.list" /></a></li>
				<li><a class="fNiv" href="searchTemplate/listCachedTime.do"><spring:message code="master.page.cachedTime.list" /></a></li>
				<li><a class="fNiv" href="administrator/chorbi/list.do"><spring:message code="master.page.list.chorbi" /></a></li>
				<li><a class="fNiv"><spring:message	code="master.page.administrator.dashboard" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/dashboardC.do"><spring:message code="master.page.administrator.dashboradC" /></a></li>
					<li><a href="administrator/dashboardB.do"><spring:message code="master.page.administrator.dashboradB" /></a></li>
					<li><a href="administrator/dashboardA.do"><spring:message code="master.page.administrator.dashboradA" /></a></li>
				</ul>
				
				<li><a class="fNiv"><spring:message code="master.page.profile" />(<security:authentication property="principal.username" />)</a>
					<ul>
						<li class="arrow"></li>
						<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
					</ul>
				</li>
			</security:authorize>
			
			<security:authorize access="hasRole('CHORBI')">
				<li><a href="chorbi/list.do"><spring:message code="master.page.list.chorbi" /></a></li>
				<li><a href="searchTemplate/list.do"><spring:message code="master.page.searchTemplate" /></a></li>
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
						<li><a href="chorbi/edit.do"><spring:message code="master.page.edit.chorbi" /></a></li>	
						<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
					</ul>
				</li>
			</security:authorize>
			
			<security:authorize access="isAnonymous()">
				<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
				<li><a href="chorbi/register.do"><spring:message code="master.page.chorbi.register" /></a></li>
			</security:authorize>
		</ul>
		<div style="padding-top:6px">
			<a href="?language=en"><img src="images/us.svg" style="height: 15px;" /></a>
			<br>
			<a href="?language=es"><img src="images/es.svg" style="height: 15px;" /></a>
		</div>
	</div>
</div>

<br/>
<br/>
<br/>
