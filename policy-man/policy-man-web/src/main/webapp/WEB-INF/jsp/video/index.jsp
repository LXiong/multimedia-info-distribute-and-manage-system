<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:forEach items="${videos }" var="video">
	<p>${video.fileName }</p>
</c:forEach>