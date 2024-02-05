package hu.andika.javaee.model.comment;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class CommentService {

    private final CommentDao commentDao;
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public CommentService(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.commentDao = new CommentDao();
    }

    public void listCommentsToBeApproved(String message) throws ServletException, IOException {
        List<CommentDto> commentDtos = commentDao.readAllCommentsToBeAuthorized();
        request.setAttribute("comments", commentDtos);
        if (message != null) {
            request.setAttribute("message", message);
        }
        String listPage = "comment_list.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
        requestDispatcher.forward(request, response);
    }

    public void authorizeCommentById() throws ServletException, IOException {
        Integer commentId = Integer.parseInt(request.getParameter("id"));
        Optional<Comment> optionalComment = commentDao.readById(commentId);
        if (optionalComment.isPresent()) {
            commentDao.authorizeById(commentId);
            String message = "Comment with Id: " + commentId + " has been successfully authorized!";
            request.setAttribute("message", message);
            listCommentsToBeApproved(message);
        }
    }

    public void deleteCommentById() throws ServletException, IOException {
        Integer commentId = Integer.parseInt(request.getParameter("id"));
        Optional<Comment> optionalComment = commentDao.readById(commentId);
        if (optionalComment.isPresent()) {
            commentDao.deleteById(commentId);
            String message = "Comment with Id: " + commentId + " has been successfully deleted!";
            request.setAttribute("message", message);
            listCommentsToBeApproved(message);
        }
    }

}
