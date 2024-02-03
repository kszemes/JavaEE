package hu.andika.javaee.model.user;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserServices {
	private final UserDao userDAO;
	private final HttpServletRequest request;
	private final HttpServletResponse response;
	
	public UserServices(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		userDAO = new UserDao();
	}

	public void listUser()
			throws ServletException, IOException {
		listUser(null);
	}

	public void listUser(String message)
			throws ServletException, IOException {
		List<User> listUsers = userDAO.readAll();

		request.setAttribute("listUsers", listUsers);

		if (message != null) {
			request.setAttribute("message", message);
		}

		String listPage = "user_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);

		requestDispatcher.forward(request, response);

	}

	public void createUser() throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		Optional<User> existUser = userDAO.findByUserName(userName);
		
		if (existUser.isPresent()) {
			String message = "Could not create user. A user with name "
								+ userName + " already exists";
			request.setAttribute("message", message);
			RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
			dispatcher.forward(request, response);
			
		} else {		
			User newUser = new User(userName, password, Role.REGULARUSER);
			userDAO.create(newUser);
			listUser("New user created successfully");
		}

	}

	public void editUser() throws ServletException, IOException {
		Integer userId = Integer.parseInt(request.getParameter("id"));
		Optional<User> user = userDAO.readById(userId);

		if (user.isPresent()) {
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
		Role role = Role.valueOf(request.getParameter("role"));

		User userById = userDAO.readById(id).orElse(null);
		User userByName = userDAO.findByUserName(userName).orElse(null);

		Integer userByIdId = (userById != null) ? userById.getId() : null;
		Integer userByNameId = (userByName != null) ? userByName.getId() : null;

        if (!Objects.equals(userByNameId, userByIdId)) {
			String message = "Could not update user. User with name " + userName + " already exists.";
			request.setAttribute("message", message);

			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);

		} else {
			User user = new User(id, userName, password, role);
			userDAO.update(user);

			String message = "User has been updated successfully";
			listUser(message);
		}
	}

	public void deleteUser() throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("id"));
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
			request.getSession().setAttribute("userName", userName);
			request.getSession().setAttribute("loggedInUser", loggedInUser);
			RequestDispatcher dispatcher = (user.getRole().label.equals("Admin")) ? request.getRequestDispatcher("/admin/") : request.getRequestDispatcher("/user/");
			dispatcher.forward(request, response);

		} else {
			String message = "Login failed!";
			request.setAttribute("message", message);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);			
		}
	}
}
