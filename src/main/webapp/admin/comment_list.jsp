<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
	<jsp:include page="/fragments/page_head.jsp">
		<jsp:param name="pageTitle" value="Manage Unauthorized Comments" />
	</jsp:include>
<body>
<div class="container">
	<jsp:directive.include file="/fragments/header.jsp" />
	<div class="row">&nbsp;</div>
	<div class="row">
		<div class="col text-center">
			<h3>Poi Management</h3>
		</div>
	</div>
	<div class="row">&nbsp;</div>
	<c:if test="${message != null}">
		<div class="row">
			<div class="col text-center text-success">	
				<h4>${message}</h4>
			</div>
		</div>
	</c:if>
	<div class="row justify-content-center">
		<table class="table table-bordered table-striped table-hover w-auto align-middle">
			<thead class="table-success">
				<tr>
					<th>Nr.</th>
					<th>Id</th>
					<th>Content</th>
					<th>User Id</th>
					<th>User Name</th>
					<th>Poi Id</th>
					<th>Poi Name</th>
					<th>Authorized?</th>
					<th>Actions?</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="comment" items="${comments}" varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${comment.getId()}</td>
					<td>${comment.getContent()}</td>
					<td>${comment.getUserId()}</td>
					<td>${comment.getUserName()}</td>
					<td>${comment.getPointOfInterestId()}</td>
					<td>${comment.getPoiName()}</td>
					<td>${comment.isAuthorized()}</td>
					<td>
						<row class='d-flex'>
							<a class='btn btn-warning' href="authorize_comment?id=${comment.id}">Autrhorize</a> &nbsp;
							<form method="post" action="delete_comment?=id">
								<input type="hidden" name="id" value="${comment.id}">
								<button class='btn btn-danger' type="submit" onclick="return confirm('Are you sure you want to delete this comment?')">Delete</button>
							</form>
						</row>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<jsp:directive.include file="/fragments/footer.jsp" />
</div>
</body>
</html>