<%--
 * cancel.tag
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
 
<%@ tag language="java" body-content="empty" %>
 
 <%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%--Script --%>
<script type="text/javascript">
	function relativeRedir(loc) {	
		var b = document.getElementsByTagName('base');
		if (b && b[0] && b[0].href) {
  			if (b[0].href.substr(b[0].href.length - 1) == '/' && loc.charAt(0) == '/')
    		loc = loc.substr(1);
  			loc = b[0].href + loc;
		}
		window.location.replace(loc);
	}
</script>

<%-- Attributes --%> 
 
<%@ attribute name="code" required="true" %>
<%@ attribute name="url" required="true" %>

<%-- Definition --%>

<button type="button" onclick="javascript: relativeRedir('${url}')" class="btn btn-default" >
	<spring:message code="${code}" />
</button>
