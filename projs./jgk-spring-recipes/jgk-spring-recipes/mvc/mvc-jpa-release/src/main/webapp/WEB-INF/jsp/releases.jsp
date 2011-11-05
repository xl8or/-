<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
This is releases here
Some
${releases}
<br/>
<c:forEach varStatus="status" var="release" items="${releases}">
<br/>
	${status.count} Release ${release}
</c:forEach>