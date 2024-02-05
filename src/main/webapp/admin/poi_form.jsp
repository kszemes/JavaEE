<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<jsp:include page="/fragments/page_head.jsp">
    <jsp:param name="pageTitle" value="Manage Pois"/>
</jsp:include>
<body>
<div class="container">
    <jsp:directive.include file="/fragments/header.jsp"/>

    <div class="row m-3">
        <div class="col text-center">
            <h2><c:out value="${poi != null ? 'Edit Poi' : 'Create New Poi'}"/></h2>
        </div>
    </div>

    <c:if test="${poi != null}">
        <form action="update_poi" method="post" style="max-width: 400px; margin: 0 auto;">
        <input type="hidden" name="id" value="${poi.id}">
    </c:if>
    <c:if test="${poi == null}">
        <form action="create_poi" method="post" style="max-width: 400px; margin: 0 auto;">
    </c:if>
            <div class="form-group row">
                <label class="col-sm-4 col-form-label">Name:</label>
                <div class="col-sm-8">
                    <input type="text" name="name" size="20" value="${poi.name}" class="form-control" required/>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-4 col-form-label">Location:</label>
                <div class="col-sm-8">
                    <input type="text" name="location" size="20" value="${poi.location}" class="form-control" required/>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-4 col-form-label">Type:</label>
                <div class="col-sm-8">
                    <input type="text" name="type" size="20" value="${poi.type}" class="form-control" required/>
                </div>
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