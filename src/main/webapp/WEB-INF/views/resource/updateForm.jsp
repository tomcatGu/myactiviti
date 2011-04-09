<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title>Create Resource</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/themes/icon.css">


<script type="text/javascript"
	src="<c:url value="/scripts/jquery-1.4.2.min.js" /> "></script>
<script type="text/javascript"
	src="<c:url value="/scripts/json.min.js" /> "></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/locale/easyui-lang-zh_CN.js"></script>

</head>
<body>
<div class="container">
<h1>Update User</h1>
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



	<p><input id="update" type="submit" value="Update" /></p>
	</fieldset>
</form:form></div>
<hr>

</div>

</body>

<script type="text/javascript">	
		$(function(){
			// check name availability on focus lost
			$('#name').blur(function() {
				if ($('#name').val()) {	
					checkAvailability();
				}
			});
			$("#resource").submit(function() {
				var resource = $(this).serializeObject();
				//user="{username:'123',realname:'123',password:'123',roles:{{id:'1',name:'admin'},{id:'2',name:'user'}}";
				//alert($("form").serialize());
				//user.roles=[{"id":'1',"name":'admin'}];
				
				alert(JSON.stringify(resource));
				$.postJSON("resource/edit", resource, function(data) {
					//$("#assignedId").val(data.id);
					//showPopup();
					alert(data);
				});
				return false;				
			});
		});


		
		function closePopup() {
			$('#popup').fadeOut('fast');
			$('#mask').fadeOut('fast');
			$('body').css('overflow','auto');
			resetForm();
		}

		function resetForm() {
			$('#user')[0].reset();
		}
		
	</script>

</html>
