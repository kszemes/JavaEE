package hu.andika.javaee.model.comment;

import hu.andika.javaee.model.poi.Poi;
import hu.andika.javaee.model.user.User;
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

    private Integer getLoggedInUserId(){
        Optional<User> user = (Optional<User>) request.getSession().getAttribute("loggedInUser");
        return user.get().getId();
    }

    public void listCommentsByUser(String message) throws ServletException, IOException {
        Integer userId = getLoggedInUserId();
        List<CommentDto> commentDtos = commentDao.readAllCommentsByUserId(userId);
        request.setAttribute("comments", commentDtos);
        if (message != null) {
            request.setAttribute("message", message);
        }
        String listPage = "comment_list.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
        requestDispatcher.forward(request, response);
    }

    public void listCommentsByPoiId(String message) throws ServletException, IOException {
        Integer poiId = Integer.valueOf(request.getParameter("poiId"));
        List<CommentDto> commentDtos = commentDao.readAllCommentsByPoiId(poiId);
        request.setAttribute("comments", commentDtos);
        if (message != null) {
            request.setAttribute("message", message);
        }
        String listPage = "comment_list.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
        requestDispatcher.forward(request, response);
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

    public void showNewCommentForm() throws ServletException, IOException {
        String newCommentPage = "comment_form.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(newCommentPage);
        requestDispatcher.forward(request, response);
    }

    public void createComment() throws ServletException, IOException {
        String message;
        Integer poiId = Integer.valueOf(request.getParameter("poiId"));
        Integer userId = getLoggedInUserId();
        String content = request.getParameter("content");
        Comment comment = new Comment(content, userId, poiId, false);
        System.out.println(comment);
        try {
            commentDao.create(comment);
            message = "Comment has been created successfully with id: " + comment.getId();
            request.setAttribute("message", message);
            listCommentsByUser(message);
        } catch (Exception e) {
            message = e.getMessage();
            listCommentsByUser(message);
        }
    }
}
