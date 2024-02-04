<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
	<jsp:include page="/fragments/page_head.jsp">
		<jsp:param name="pageTitle" value="Login Page" />
	</jsp:include>
<body>
<div class="container">
	<jsp:directive.include file="/fragments/header.jsp" />
	<div class="row">&nbsp;</div>
	<div class="row">
		<div class="col text-center"><h2>Login</h2></div>
	</div>
	<div class="row">&nbsp;</div>
	<c:if test="${message != null}">
		<div class="row">		
			<div class="col text-center"><h4 class="message">${message}</h4></div>
		</div>
	</c:if>
	<form action="login" method="post" style="max-width: 400px; margin: 0 auto;">
		<div class="border border-2 border-secondary rounded p-4">
			<div class="input-group input-group-lg">
				<span class="input-group-text" >Username:</span>
				<input type="text" name="userName" class="form-control" required>
			</div>
			<div class="input-group input-group-lg">
				<span class="input-group-text" >Password:</span>
				<input type="text" name="password" class="form-control" required>
			</div>
			<div class="row">
				<div class="col text-center mt-4">
					<button type="submit" class="btn btn-primary">Login</button>
				</div>
			</div>
		</div>
	</form>
	<jsp:directive.include file="/fragments/footer.jsp" />
</div>	
</body>
</html>