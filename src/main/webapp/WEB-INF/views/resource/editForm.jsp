<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>Create Resource</title>


<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath}/css/ui-lightness/jquery-ui-1.8.7.custom.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath}/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath}/css/ui.multiselect.css" />


<script
	src="${pageContext.request.contextPath}/scripts/jquery-1.4.4.min.js"
	type="text/javascript"></script>

<script
	src="${pageContext.request.contextPath}/scripts/jquery-ui-1.8.7.custom.min.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/scripts/i18n/grid.locale-cn.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/scripts/jquery.jqGrid.min.js"
	type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/scripts/json2.js"
	type="text/javascript"></script>
</head>
<body>
<div class="container">
<h1>Create User</h1>
<div class="span-12 last"><form:form modelAttribute="resource"
	method="post">
	<fieldset><legend>Resource Fields</legend>


	<p><form:label id="idLabel" for="id" path="id"
		cssErrorClass="error">id</form:label><br />
	<form:input path="id" /><form:errors path="id" /></p>
	<p><form:label id="nameLabel" for="name" path="name"
		cssErrorClass="error">name</form:label><br />
	<form:input path="name" /><form:errors path="name" /></p>

	<p><form:label id="typeLabel" for="type" path="type"
		cssErrorClass="error">type</form:label><br />
	<form:input path="type" /><form:errors path="type" /></p>
	<p><form:label for="valueLabel" path="value" cssErrorClass="error">value</form:label><br />
	<form:input path="value" /><form:errors path="value" /></p>
	<c:forEach items="${allRoles}" var="cRole" varStatus="vs">
		<c:set var="test" value="false"></c:set>
		<c:forEach var="role" items="${resource.roles}">
			<c:if test="${cRole.id == role.id}">
				<c:set var="test" value="true"></c:set>
			</c:if>
		</c:forEach>

		<c:if test="${test == 'true'}">
			<input type="checkbox" name="roles" value="${cRole.id}"
				checked="true">${cRole.name}</input>
		</c:if>
		<c:if test="${test != 'true'}">
			<input type="checkbox" name="roles" value="${cRole.id}">${cRole.name}</input>
		</c:if>
	</c:forEach></fieldset>
</form:form></div>


</div>
<div id="dialog-message">saved!</div>
</body>

<script type="text/javascript">
	$(function() {
		// check name availability on focus lost

		$("#resource").submit(function() {
			var resource = $(this).serializeObject();
			//alert(JSON.stringify(resource));
			$.ajax({
				type : "PUT",
				url : resource.id,
				contentType : 'application/json',
				data : JSON.stringify(resource),
				async : false,
				success : function(msg) {
					$("#dialog-message").dialog({
						modal : true,
						buttons : {
							Ok : function() {
								$(this).dialog("close");
							}
						}

					});
				}
			});
			return false;
		});

	});
</script>

</html>
