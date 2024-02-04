<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
	<jsp:include page="/fragments/page_head.jsp">
		<jsp:param name="pageTitle" value="Points of Interests" />
	</jsp:include>
<body>
<div class="container">
	<jsp:directive.include file="/fragments/header.jsp" />
	<div>Hello User!</div>
	<jsp:directive.include file="/fragments/footer.jsp" />
</div>	
</body>
</html>