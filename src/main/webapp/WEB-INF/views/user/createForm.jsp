<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title>Create User</title>
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
<h1>Create User</h1>
<div class="span-12 last"><form:form modelAttribute="user"
	method="post">
	<fieldset><legend>Account Fields</legend>
	<p><form:label id="nameLabel" for="username" path="username"
		cssErrorClass="error">username</form:label><br />
	<form:input path="username" /><form:errors path="username" /></p>

	<p><form:label id="real" for="realname" path="realname"
		cssErrorClass="error">realname</form:label><br />
	<form:input path="realname" /><form:errors path="realname" /></p>
	<p><form:label for="password" path="password" cssErrorClass="error">password</form:label><br />
	<form:input path="password" /><form:errors path="password" /></p>
	<p><form:label for="roles" path="roles" cssErrorClass="error">roles</form:label><br />

	<form:checkboxes items="${user.roles}" path="roles" itemValue='id'
		itemLabel='name' />
	<p><input id="create" type="submit" value="Create" /></p>
	</fieldset>
</form:form></div>
<hr>
<ul>
	<li><a href="?locale=en_us">us</a> | <a href="?locale=en_gb">gb</a>
	| <a href="?locale=es_es">es</a> | <a href="?locale=de_de">de</a></li>
</ul>
</div>
<div id="mask" style="display: none;"></div>
<div id="popup" style="display: none;">
<div class="span-8 last">
<form>
<fieldset>
<p><label for="assignedId">Assigned Id</label><br />
<input id="assignedId" type="text" readonly="readonly" /></p>
<p><label for="confirmedName">Name</label><br />
<input id="confirmedName" type="text" readonly="readonly" /></p>
<p><label for="confirmedBalance">Balance</label><br />
<input id="confirmedBalance" type="text" readonly="readonly" /></p>
<p><label for="confirmedRenewalDate">Renewal Date</label><br />
<input id="confirmedRenewalDate" type="text" readonly="readonly" /></p>
</fieldset>
</form>
<a href="#" onclick="closePopup();">Close</a></div>
</div>
</body>

<script type="text/javascript">
	$(document).ready(function() {
		// check name availability on focus lost
		$('#name').blur(function() {
			if ($('#name').val()) {
				checkAvailability();
			}
		});
		$("#user").submit(function() {
			var user = $(this).serializeObject();
			//user="{username:'123',realname:'123',password:'123',roles:{{id:'1',name:'admin'},{id:'2',name:'user'}}";
			//alert($("form").serialize());
			//user.roles=[{"id":'1',"name":'admin'}];

			alert(JSON.stringify(user));
			$.postJSON("user/create", user, function(data) {
				//$("#assignedId").val(data.id);
				//showPopup();
				alert(data);
			});
			return false;
		});
	});

	function checkAvailability() {
		$.getJSON("account/availability", {
			name : $('#name').val()
		}, function(availability) {
			if (availability.available) {
				fieldValidated("name", {
					valid : true
				});
			} else {
				fieldValidated("name", {
					valid : false,
					message : $('#name').val() + " is not available, try "
							+ availability.suggestions
				});
			}
		});
	}

	function fieldValidated(field, result) {
		if (result.valid) {
			$("#" + field + "Label").removeClass("error");
			$("#" + field + "\\.errors").remove();
			$('#create').attr("disabled", false);
		} else {
			$("#" + field + "Label").addClass("error");
			if ($("#" + field + "\\.errors").length == 0) {
				$("#" + field).after(
						"<span id='" + field + ".errors'>" + result.message
								+ "</span>");
			} else {
				$("#" + field + "\\.errors").html(
						"<span id='" + field + ".errors'>" + result.message
								+ "</span>");
			}
			$('#create').attr("disabled", true);
		}
	}

	function showPopup() {
		$.getJSON("account/" + $("#assignedId").val(), function(account) {
			$("#confirmedName").val(account.name);
			$("#confirmedBalance").val(account.balance);
			$("#confirmedEquityAllocation").val(account.equityAllocation);
			$("#confirmedRenewalDate").val(account.renewalDate);
		});
		$('body').css('overflow', 'hidden');
		$('#popup').fadeIn('fast');
		$('#mask').fadeIn('fast');
	}

	function closePopup() {
		$('#popup').fadeOut('fast');
		$('#mask').fadeOut('fast');
		$('body').css('overflow', 'auto');
		resetForm();
	}

	function resetForm() {
		$('#user')[0].reset();
	}
</script>

</html>
