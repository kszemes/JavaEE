<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<jsp:include page="/fragments/page_head.jsp">
    <jsp:param name="pageTitle" value="Points Of Interests"/>
</jsp:include>
<body>
<div class="container">
    <jsp:directive.include file="/fragments/header.jsp"/>
    <div class="row">&nbsp;</div>
    <div class="row">
        <div class="col text-center">
            <h3>Points Of Interests</h3>
        </div>
    </div>
    <div class="row">&nbsp;</div>
    <div class="container text-center">
        <div class="row justify-content-center">
            <div class='col-auto'>
                <div class="row">
                    <div class="col-auto">
                        <form method="get" action="list_pois_by_location" class='d-flex'>
                            <select name="location" class="form-select" required>
                                <option value="" selected>Select location for search!</option>
                                <c:forEach items="${locations}" var="location">
                                    <option value="${location}">${location}</option>
                                </c:forEach>
                            </select>
                            <button type="submit" class="btn btn-primary">Search</button>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-auto">
                <form method="get" action="list_pois">
                    <button type="submit" class="btn btn-secondary">Clear Search</button>
                </form>
            </div>
            <div class='col-auto'>
                <div class="row">
                    <div class="col-auto">
                        <form method="get" action="list_pois_by_type" class='d-flex'>
                            <select name="type" class="form-select" required>
                                <option value="" selected>Select type for search!</option>
                                <c:forEach items="${types}" var="type">
                                    <option value="${type}">${type}</option>
                                </c:forEach>
                            </select>
                            <button type="submit" class="btn btn-primary">Search</button>
                        </form>
                    </div>
                </div>
            </div>
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
                <th>Name</th>
                <th>Location</th>
                <th>Type</th>
                <th>Likes</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="poi" items="${pois}" varStatus="status">
                <tr>
                    <td>${status.index + 1}</td>
                    <td>${poi.id}</td>
                    <td>${poi.name}</td>
                    <td>${poi.location}</td>
                    <td>${poi.type}</td>
                    <td>${poi.likes}</td>
                    <td>
                        <div class='d-flex'>
                            <form method="post" action="like_poi?id=${poi.id}">
                                <input type="hidden" name="id" value="${poi.id}">
                                <button class='btn btn-danger' href="like_poi?id=${poi.id}">Like</button> &nbsp;
                            </form>
                            <a href='add_comment?poiId=${poi.id}' class="btn btn-warning me-2">Add comment</a>
                            <a href='view_comments?poiId=${poi.id}' class="btn btn-info">View comments</a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <jsp:directive.include file="/fragments/footer.jsp"/>
</div>
</body>
</html>