<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<body>
<h2>Rest links below</h2>
<%= request.getContextPath() %>/rest/catalog/
<br/>
<br/>
<br/>
<a href="<%= request.getContextPath() %>/rest/catalog/">catalog</a>
<br/>
<a href="<%= request.getContextPath() %>/rest/requestInfo">requestInfo</a>
<br/>
<a href="<%= request.getContextPath() %>/rest/methoddesc?methodname=catalog">methoddesc?methodname=catalog</a>
<br/>
<a href="<%= request.getContextPath() %>/rest/methoddesc?methodname=methoddesc">methoddesc?methodname=methoddesc</a>
<br/>
<a href="<%= request.getContextPath() %>/rest/methoddesc?methodname=requestInfo">methoddesc?methodname=requestInfo</a>
</body>
</html>
