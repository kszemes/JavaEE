package hu.andika.javaee.model.comment;

import hu.andika.javaee.utils.DatabaseInit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentDao {

    protected String create;
    protected String read;
    protected String readAll;
    protected String update;
    protected String authorize;
    protected String delete;

    private static final DatabaseInit databaseInit = new DatabaseInit();
    private static final Connection connection = databaseInit.getConnection();

    public static Logger logger = LogManager.getLogger();

    public Connection getConnection(){
        return connection;
    }

    public CommentDao() {
        this.create = "INSERT INTO comment ";
        this.read = "SELECT * FROM comment WHERE id = ?";
        this.readAll = "SELECT * FROM comment";
        this.update = "UPDATE comment SET ";
        this.authorize = "UPDATE comment SET isAuthorized = 'true' WHERE id = ?";
        this.delete = "DELETE FROM comment WHERE id = ?";
    }

    public Comment create(Comment comment) {
        try (PreparedStatement pst =
                     getConnection().prepareStatement(
                             String.format("%s(content, userId, pointOfInterestId, isAuthorized) values (?, ?, ? ,?)", this.create), Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, comment.getContent());
            pst.setInt(2, comment.getUserId());
            pst.setInt(3, comment.getPointOfInterestId());
            pst.setString(4, String.valueOf(comment.isAuthorized()));
            int affectedRow = pst.executeUpdate();
            if (affectedRow <= 0) {
                logger.error("SQL Insert failed!");
            }
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                comment.setId(generatedKeys.getInt(1));
            }
            logger.info("The Comment successfully created: " + comment + " with ID: " + comment.getId());
            return comment;
        } catch (SQLException e) {
            logger.catching(Level.ERROR, e);
        }
        return null;
    }

    public Optional<Comment> readById(Integer id) {
        try (PreparedStatement pst = getConnection().prepareStatement(this.read)) {
            Comment comment = null;
            pst.setInt(1, id);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                comment = new Comment(
                        resultSet.getInt("id"),
                        resultSet.getString("content"),
                        resultSet.getInt("userId"),
                        resultSet.getInt("pointOfInterestId"),
                        Boolean.parseBoolean(resultSet.getString("isAuthorized"))
                );
            }
            logger.info(comment.getId() == null ? "There is no Comment with ID: " + id : "Comment found: " + comment);
            return Optional.of(comment);
        } catch (Exception e) {
            logger.error("Exception is: " + e);
            logger.error("There is no Comment with ID: " + id);
        }
        return Optional.empty();
    }

    public List<Comment> readAll() {
        List<Comment> comments = null;
        try (PreparedStatement pst = getConnection().prepareStatement(this.readAll)) {
            comments = new ArrayList<>();
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                comments.add(
                    new Comment(
                        resultSet.getInt("id"),
                        resultSet.getString("content"),
                        resultSet.getInt("userId"),
                        resultSet.getInt("pointOfInterestId"),
                        Boolean.parseBoolean(resultSet.getString("isAuthorized"))
                    )
                );
            }
            logger.info("Found " + comments.size() + " Comments from database");
            return comments;
        } catch (Exception e) {
            logger.error("There is no Comments in the table!");
        }
        return comments;
    }

    public List<CommentDto> readAllCommentsToBeAuthorized() {
        List<CommentDto> comments = null;
        try (PreparedStatement pst = getConnection().prepareStatement(
                "select c.id, c.content, c.userId, u.userName, c.pointOfInterestId, p.name, c.isAuthorized " +
                        "from comment as c " +
                        "left join user u on u.id = c.userId " +
                        "left join poi p on c.pointOfInterestId = p.id " +
                        "where c.isAuthorized = 'false'")) {
            comments = new ArrayList<>();
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                comments.add(
                    new CommentDto(
                        resultSet.getInt("id"),
                        resultSet.getString("content"),
                        resultSet.getInt("userId"),
                        resultSet.getString("userName"),
                        resultSet.getInt("pointOfInterestId"),
                        resultSet.getString("name"),
                        Boolean.parseBoolean(resultSet.getString("isAuthorized"))
                    )
                );
            }
            logger.info("Found " + comments.size() + " Comments from database to be Authorized.");
            return comments;
        } catch (Exception e) {
            logger.error("There is no Comments in the table!");
        }
        return comments;
    }

        public List<Comment> readAllByUserId(Integer userId) {
        List<Comment> comments = null;
        try (PreparedStatement pst = getConnection().prepareStatement("SELECT * FROM comment where userId = ?")) {
            pst.setInt(1, userId);
            comments = new ArrayList<>();
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                comments.add(
                    new Comment(
                        resultSet.getInt("id"),
                        resultSet.getString("content"),
                        resultSet.getInt("userId"),
                        resultSet.getInt("pointOfInterestId"),
                        Boolean.parseBoolean(resultSet.getString("isAuthorized"))
                    )
                );
            }
            logger.info("Found " + comments.size() + " Comments from database");
            return comments;
        } catch (Exception e) {
            logger.error("There is no Comments in the table with userID: " + userId);
        }
        return comments;
    }

    public List<Comment> readAllByPoiId(Integer poiId) {
        List<Comment> comments = null;
        try (PreparedStatement pst = getConnection().prepareStatement("SELECT * FROM comment where pointOfInterestId = ?")) {
            pst.setInt(1, poiId);
            comments = new ArrayList<>();
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                comments.add(
                    new Comment(
                        resultSet.getInt("id"),
                        resultSet.getString("content"),
                        resultSet.getInt("userId"),
                        resultSet.getInt("pointOfInterestId"),
                        Boolean.parseBoolean(resultSet.getString("isAuthorized"))
                    )
                );
            }
            logger.info("Found " + comments.size() + " Comments from database");
            return comments;
        } catch (Exception e) {
            logger.error("Exception is: " + e);
            logger.error("There is no Comments in the table with poiId: " + poiId);
        }
        return comments;
    }

    public Comment update(Comment comment) {
        try (PreparedStatement pst = getConnection().prepareStatement(
                String.format("%s content = ?, userId = ?, pointOfInterestId = ?, isAuthorized = ? WHERE id = ?", this.update),
                Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, comment.getContent());
            pst.setInt(2, comment.getUserId());
            pst.setInt(3, comment.getPointOfInterestId());
            pst.setString(4, String.valueOf(comment.isAuthorized()));
            pst.setInt(5, comment.getId());
            int affectedRow = pst.executeUpdate();
            if (affectedRow <= 0) {
                logger.error("SQL Update failed!");
            }
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                comment.setId(generatedKeys.getInt(1));
            }
            logger.info("The Comment updated: " + comment);
            return comment;
        } catch (SQLException e) {
            logger.catching(Level.ERROR, e);
        }
        return null;
    }

    public void deleteById(Integer id) {
        try (PreparedStatement pst = getConnection().prepareStatement(this.delete)) {
            pst.setInt(1, id);
            int affectedRow = pst.executeUpdate();
            if (affectedRow <= 0) {
                logger.error("SQL Delete failed!");
            }
            logger.info("The Comment with ID: " + id + " was successfully deleted!");
        } catch (SQLException e) {
            logger.catching(Level.ERROR, e);
        }
    }

    public void authorizeById(Integer id) {
        try (PreparedStatement pst = getConnection().prepareStatement(this.authorize)) {
            pst.setInt(1, id);
            int affectedRow = pst.executeUpdate();
            if (affectedRow <= 0) {
                logger.error("SQL Update failed!");
            }
            logger.info("The Comment successfully authorized!");
        } catch (SQLException e) {
            logger.catching(Level.ERROR, e);
        }
    }
}