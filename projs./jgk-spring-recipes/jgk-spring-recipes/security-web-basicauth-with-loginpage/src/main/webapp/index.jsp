<%@page import="org.springframework.security.web.authentication.WebAuthenticationDetails"%>
<%@page import="org.springframework.security.core.userdetails.UserDetails"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
<title><%= request.getUserPrincipal().getName() %></title>
</head>
<body>
<h2>Basic Authentication with a Login Page!</h2>

<%
Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
String username = null;
Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();
WebAuthenticationDetails details = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
String remoteAddress = details.getRemoteAddress();
String sessionId = details.getSessionId();
if (principal instanceof UserDetails) {
 username = ((UserDetails)principal).getUsername();
} else {
  username = principal.toString();
}
%>
Username:  <%= username %>
<br/>
Credentials:  <%= credentials %>
<br/>
Details:  <%= details %>
<br/>
Remote Address:  <%= remoteAddress %>
<br/>
Session ID:  <%= sessionId %>
<br/>
<a href='<%= request.getContextPath() %>/logout'>logout</a>
<button onclick="location.href='<%= request.getContextPath() %>/admin'" 
<c:if test='<%= !request.isUserInRole("ROLE_ADMIN") %>'>disabled='true'</c:if>
>
Administrator</button>
<a href='<%= request.getContextPath() %>/admin'>Administration</a>
</body>
</html>
