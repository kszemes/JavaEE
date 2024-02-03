<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="row">z
	<a href="${pageContext.request.contextPath}/">
		Home
	</a>
</div>

<nav class="navbar navbar-expand-lg bg-dark navbar-dark">
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#topNavbar">
		<span class="navbar-toggler-icon"></span>
	</button>
	
	<div class="collapse navbar-collapse" id="topNavbar">
		<form action="search" method="get" class="form-inline">
			<input type="search" name="keyword" class="form-control mr-sm-2 mt-1" placeholder="keyword"/>
			<input type="submit" value="Search" class="btn btn-outline-success mt-1" />	
		</form>
		<ul class="navbar-nav">
			<c:if test="${loggedInUser == null}">
				<li class="nav-item">
					<a href="login" class="nav-link">Sign In</a>
				</li>
				<li class="nav-item">
					<a href="register" class="nav-link">Register</a>
				</li>			
			</c:if>
			
			<c:if test="${loggedInUser != null}">
				<li class="nav-item">
					<a href="view_profile" class="nav-link">Welcome, ${loggedInUser.fullname}</a>
				</li>
				
				<li class="nav-item">
					<a href="view_orders" class="nav-link">My Orders</a>
				</li>
				
				<li class="nav-item">
					<a href="logout" class="nav-link">Logout</a>
				</li>
			</c:if>
			
			<li class="nav-item">
				<a href="view_cart" class="nav-link">Cart</a>
			</li>			
		</ul>
	</div>
</nav>