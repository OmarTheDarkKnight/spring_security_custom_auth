<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
	<head>
		<title>Admin Home Page</title>
	</head>
	<body>
		<h2>Admin Home Page</h2>
		<hr>
		<p>Welcome to the Admin home page!</p>
		<hr>

		<!-- display user name and role -->
		<p>
			User: <security:authentication property="principal.username" />
			<br><br>
			Role(s): <security:authentication property="principal.authorities" />
			<br><br>
			First name: ${user.firstName}, Last name: ${user.lastName}, Email: ${user.email}
		</p>

		<security:authorize access="hasRole('ROLE_1')">
			<p>Only for Admin peeps</p>
		</security:authorize>

		<hr>

		<!-- Add a logout button -->
		<form:form action="${pageContext.request.contextPath}/logout"
				   method="POST">
			<input type="submit" value="Logout" />
		</form:form>

	</body>
</html>









