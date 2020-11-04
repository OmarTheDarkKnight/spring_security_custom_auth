<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
	<title>luv2code Company Home Page</title>
</head>
<body>
	<h2>luv2code Company Home Page</h2>
	<hr>
	<p>Welcome to the luv2code company home page!</p>

	<hr>
	<!-- display user name and role -->
	<p>
		User: <security:authentication property="principal.username" />
		<br><br>
		Role(s): <security:authentication property="principal.authorities" />
		<br><br>
		First name: ${user.firstName}, Last name: ${user.lastName}, Email: ${user.email}
	</p>

	<security:authorize access="hasRole('ROLE_2')">
		<p>(Only for User peeps)</p>
	</security:authorize>

	<hr>
	<!-- Add a logout button -->
	<form:form action="${pageContext.request.contextPath}/logout"
			   method="POST">
		<input type="submit" value="Logout" />
	</form:form>
</body>

</html>