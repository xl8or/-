<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<html>
<body>
<h2>Hello World!</h2>
<%
ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(application);
for( String bn : ctx.getBeanDefinitionNames() ) {
%>	
	<%=bn %><br/>
<%	
}
%>


<br/>
<%= ctx %>
</body>
</html>
