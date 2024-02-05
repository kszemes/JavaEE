package hu.andika.javaee.controller.admin.comment;

import hu.andika.javaee.model.comment.CommentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/delete_comment")
public class DeleteCommentServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CommentService commentService = new CommentService(request, response);
		commentService.deleteCommentById();
	}

}