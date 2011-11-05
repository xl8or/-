<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html><head><title>Login Page</title>
<style type="text/css">
.error { color:red;}
</style>
</head>
<body onload='document.f.j_username.focus();'>


<%-- ${param.login_error} --%>
<c:if test="${!empty param.login_error}">
<p class='error'>Your login attempt was not successful, try again.<br/><br/>Reason: Bad credentials</p>
</c:if>

 <form name='f' action='<%= request.getContextPath() %>/j_spring_security_check' method='POST'>
 <table>
    <tr><td>User:</td><td><input type='text' name='j_username' value=''></td></tr>
    <tr><td>Password:</td><td><input type='password' name='j_password'/></td></tr>
    <tr><td colspan='2'><input name="submit" type="submit"/></td></tr>
    <tr><td colspan='2'><input name="reset" type="reset"/></td></tr>
  </table>
</form></body></html>