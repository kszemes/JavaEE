<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<jsp:include page="/fragments/page_head.jsp">
    <jsp:param name="pageTitle" value="Manage Users"/>
</jsp:include>
<body>
<div class="container">
    <jsp:directive.include file="/fragments/header.jsp"/>

    <div class="row m-3">
        <div class="col text-center">
            <h2><c:out value="${user != null ? 'Edit User' : 'Create New User'}"/></h2>
        </div>
    </div>

    <c:if test="${user != null}">
        <form action="update_user" method="post" style="max-width: 400px; margin: 0 auto;">
        <input type="hidden" name="id" value="${user.id}">
    </c:if>
    <c:if test="${user == null}">
        <form action="create_user" method="post" style="max-width: 400px; margin: 0 auto;">
    </c:if>
            <div class="form-group row">
                <label class="col-sm-4 col-form-label">Username:</label>
                <div class="col-sm-8">
                    <input type="text" name="userName" size="20" value="${user.userName}" class="form-control" required/>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-4 col-form-label">Password:</label>
                <div class="col-sm-8">
                    <input type="text" name="password" size="20" value="${user.password}" class="form-control" required/>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-4 col-form-label">Role:</label>
                <select name="role" class="col-sm-8" required>
                    <c:forEach items="${roleList}" var="role">
                        <option value="${role}" <c:if test='${role eq user.role.label}'>selected='selected'</c:if> >${role}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="row m-4">
                <div class="col text-center">
                    <button type="submit" class="btn btn-primary mr-2">Save</button>
                    <button type="button" class="btn btn-secondary ml-2" onclick="history.go(-1);">Cancel</button>
                </div>
            </div>
        </form>
        <jsp:directive.include file="/fragments/footer.jsp"/>
</div>
</body>
</html>