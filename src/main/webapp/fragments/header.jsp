<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<nav class="navbar" style="background-color: #e3f2fd;">
    <div class="container-fluid">
        <a href="${pageContext.request.contextPath}" class="navbar-brand">Points of Interests</a>
        <ul class="nav justify-content-end">
            <c:if test="${loggedInUser == null}">
                <li class="nav-item">
                    <a href="login" class="nav-link">Sign In</a>
                </li>
            </c:if>
            <c:if test="${loggedInUser != null}">
                <c:choose>
                    <c:when test="${userRole == 'Admin'}">
                        <jsp:include page="/admin/menu_items.jsp" />
                    </c:when>
                    <c:otherwise>
                        <jsp:include page="/index/menu_items.jsp" />
                    </c:otherwise>
                </c:choose>
                <li class="nav-item">
                    <span class="nav-link">User: ${userName}</span>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/logout" class="nav-link">Logout</a>
                </li>
            </c:if>
        </ul>
    </div>
</nav>