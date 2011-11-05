<%@page import="org.springframework.security.access.AccessDeniedException"%>
<%
  if(!request.isUserInRole("ROLE_ADMIN")) {
	  throw new AccessDeniedException("You must be an administrator to do this");
  }
%>

This is administrator page - only ROLE_ADMIN users can be here