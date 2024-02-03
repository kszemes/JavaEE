<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
		<div class="col text-center"><h2>Login Page</h2></div>
	</div>
	
	<div class="row">&nbsp;</div>

	<c:if test="${message != null}">
		<div class="row">		
			<div class="col text-center"><h4 class="message">${message}</h4></div>
		</div>
	</c:if>
		
	<form action="login" method="post" style="max-width: 400px; margin: 0 auto;">
		<div class="border border-secondary rounded p-3">
			<div class="form-group row">
				<label class="col-sm-4 col-form-label">Email:</label>
				<div class="col-sm-8">
					<input type="email" name="email" class="form-control" required minlength="5" maxlength="64">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-4 col-form-label">Password:</label>
				<div class="col-sm-8">
					<input type="password" name="password" class="form-control" required minlength="5" maxlength="16">
				</div>
			</div>		
			<div class="row">
				<div class="col text-center">
					<button type="submit" class="btn btn-primary">Login</button>
				</div>
			</div>
		</div>
	</form>
	
	<jsp:directive.include file="/fragments/footer.jsp" />
</div>	
</body>
</html>