<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
	<jsp:include page="/fragments/page_head.jsp">
		<jsp:param name="pageTitle" value="Manage Users" />
	</jsp:include>
<body>
<div class="container">
	<jsp:directive.include file="/fragments/header.jsp" />
	<div class="row">&nbsp;</div>
	<div class="row">
		<div class="col text-center">
			<h3>Users Management</h3>
		</div>
	</div>
	<div class="row">&nbsp;</div>
	<div class='row justify-content-center'>
		<a href='new_user' class="btn btn-primary col-1">New User</a>
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
					<th>User Name</th>
					<th>Password</th>
					<th>Role</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="user" items="${users}" varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${user.id}</td>
					<td>${user.userName}</td>
					<td>${user.password}</td>
					<td>${user.role.label}</td>
					<td>
						<row class='d-flex'>
							<a class='btn btn-warning' href="edit_user?id=${user.id}">Edit</a> &nbsp;
							<form method="post" action="delete_user?=id">
								<input type="hidden" name="id" value="${user.id}">
								<button class='btn btn-danger' type="submit" onclick="return confirm('Are you sure you want to delete this user?')">Delete</button>
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