<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<% String poiId = request.getParameter("poiId");
    request.setAttribute("poiId", poiId);
%>

<!DOCTYPE html>
<html>
<jsp:include page="/fragments/page_head.jsp">
    <jsp:param name="pageTitle" value="Create Comment"/>
</jsp:include>
<body>
<div class="container">
    <jsp:directive.include file="/fragments/header.jsp"/>

    <div class="row m-3">
        <div class="col text-center">
            <h2><c:out value="${poi != null ? 'Edit Comment' : 'Create New Comment'}"/></h2>
        </div>
    </div>

    <c:if test="${comment != null}">
        <form action="update_comment" method="post" style="max-width: 400px; margin: 0 auto;">
        <input type="hidden" name="commentId" value="${comment.id}">
        <input type="hidden" name="poiId" value="${param.poiId}"/>
    </c:if>
    <c:if test="${comment == null}">
        <form action="create_comment" method="post" style="max-width: 400px; margin: 0 auto;">
    </c:if>
            <input type="hidden" name="poiId" value="${param.poiId}"/>
            <div class="form-group row">
                <label class="col-2 col-form-label">Name:</label>
                <div class="col-8">
                    <input type="text" name="content" size="120" value="${comment.content}" class="form-control" required/>
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