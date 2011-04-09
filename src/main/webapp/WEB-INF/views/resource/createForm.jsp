<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
	<p><form:label id="nameLabel" for="name" path="name"
		cssErrorClass="error">name</form:label><br />
	<form:input path="name" /><form:errors path="name" /></p>

	<p><form:label id="typeLabel" for="type" path="type"
		cssErrorClass="error">type</form:label><br />
	<form:input path="type" /><form:errors path="type" /></p>
	<p><form:label for="valueLabel" path="value" cssErrorClass="error">value</form:label><br />
	<form:input path="value" /><form:errors path="value" /></p>



	<c:forEach var="role" items="${resource.roles}">
		<input type="checkbox" name="roles" value="${role.id}">${role.name}</input>
	</c:forEach></fieldset>
</form:form></div>


</div>
<div id="dialog-message"></div>
</body>

<script type="text/javascript">	
		$(function(){
			// check name availability on focus lost
			$('#name').blur(function() {
				if ($('#name').val()) {	
					//checkAvailability();
				}
			});
			$("#resource").submit(function() {

				var resource=$(this).serializeObject();
				//alert(JSON.stringify(resource));
				

				$.postJSON("", JSON.stringify(resource),false, function(data) {


					$( "#dialog-message" ).html("Data has been saved!Its ID is:"+data.id);
					$( "#dialog-message" ).dialog({
						modal: true,
						buttons: {
							Ok: function() {
								$( this ).dialog( "close" );
							}
						}

					});
					
				});
				return false;				
			});
		});



		
	</script>

</html>
