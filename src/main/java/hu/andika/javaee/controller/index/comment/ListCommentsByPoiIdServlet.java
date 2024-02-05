package hu.andika.javaee.controller.index.comment;

import hu.andika.javaee.model.comment.CommentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/index/view_comments")
public class ListCommentsByPoiIdServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CommentService commentService = new CommentService(request, response);
		commentService.listCommentsByPoiId(null);
	}
}