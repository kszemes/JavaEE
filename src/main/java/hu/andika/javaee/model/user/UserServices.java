package hu.andika.javaee.model.user;

import hu.andika.javaee.model.comment.Comment;
import hu.andika.javaee.model.comment.CommentDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

public class UserServices {
	private final UserDao userDAO;
	private final CommentDao commentDao;
	private final HttpServletRequest request;
	private final HttpServletResponse response;
	
	public UserServices(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.userDAO = new UserDao();
		this.commentDao = new CommentDao();
	}

	public void listUser() throws ServletException, IOException {
		listUser(null);
		List<String> roleList = getRoles();
		request.getSession().setAttribute("roleList", roleList);
	}

	public List<String> getRoles(){
		List<String> roleList = new ArrayList<>();
		for (Role role : Role.values()) {
			roleList.add(role.label);
		}
		return  roleList;
	}

	public void listUser(String message) throws ServletException, IOException {
		List<User> users = userDAO.readAll();
		request.setAttribute("users", users);
		if (message != null) {
			request.setAttribute("message", message);
		}
		String listPage = "user_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
		requestDispatcher.forward(request, response);
	}

	public void showNewUserForm() throws ServletException, IOException {
		String newUserPage = "user_form.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(newUserPage);
		requestDispatcher.forward(request, response);
	}

	public void createUser() throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		Optional<User> existUser = userDAO.findByUserName(userName);
		if (existUser.isPresent()) {
			String message = "Could not create user. A user with name " + userName + " already exists";
			request.setAttribute("message", message);
			listUser(message);
		} else {		
			User newUser = new User(userName, password, Role.REGULARUSER);
			userDAO.create(newUser);
			String message = newUser.getUserName() + " has been created successfully";
			request.setAttribute("message", message);
			listUser(message);
		}
	}

	public void editUser() throws ServletException, IOException {
		Integer userId = Integer.parseInt(request.getParameter("id"));
		Optional<User> optionalUser = userDAO.readById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			String editPage = "user_form.jsp";
			request.setAttribute("user", user);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(editPage);
			requestDispatcher.forward(request, response);
		}
	}

	public void updateUser() throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String roleLabel = request.getParameter("role");
		Role role = Role.REGULARUSER;
		for (Enum<Role> r : Role.values()) {
			Role rr = Role.valueOf(r.toString());
			if (rr.getLabel().equals(roleLabel)) role = rr;
		}
		Optional<User> userByName = userDAO.findByUserName(userName);
		if (userByName.isPresent() && userByName.get().getId() != id) {
			String message = "Could not update user. User with name " + userName + " already exists.";
			request.setAttribute("message", message);
			listUser(message);
		} else {
			User user = new User(id, userName, password, role);
			userDAO.update(user);
			String message = user.getUserName() + " has been updated successfully";
			request.setAttribute("message", message);
			listUser(message);
		}
	}

	public void deleteUser() throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("id"));

		for (Comment comment : commentDao.readAllByUserId(userId)) {
			commentDao.deleteById(comment.getId());
		}

		userDAO.deleteById(userId);
		String message = "User has been deleted successfully";
		listUser(message);
	}

	public void showLogin() throws ServletException, IOException {
		String loginPage = "pages/login.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(loginPage);
		dispatcher.forward(request, response);
	}
	
	public void doLogin() throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		
		Optional<User> loggedInUser = userDAO.checkLogin(userName, password);
		if (loggedInUser.isPresent()) {
			User user = loggedInUser.get();
			String userRole = user.getRole().getLabel();
			request.getSession().setAttribute("userName", userName);
			request.getSession().setAttribute("userRole", userRole);
			request.getSession().setAttribute("loggedInUser", loggedInUser);
			RequestDispatcher dispatcher = (userRole.equals("Admin")) ?
					request.getRequestDispatcher("/admin/") :
					request.getRequestDispatcher("/index/");
			dispatcher.forward(request, response);
		} else {
			String message = "Login failed!";
			request.setAttribute("message", message);
			RequestDispatcher dispatcher = request.getRequestDispatcher("pages/login.jsp");
			dispatcher.forward(request, response);
		}
	}
}