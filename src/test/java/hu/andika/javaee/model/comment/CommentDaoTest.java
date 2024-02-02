package hu.andika.javaee.model.comment;

import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentDaoTest {

    private static CommentDao commentDao;
    private static final Comment comment = new Comment("Beautiful Place", 4, 2, false);

    @BeforeAll
    public static void setup() throws SQLException {
        commentDao = new CommentDao();
        commentDao.getConnection().setAutoCommit(false);
    }

    @Test
    @Order(1)
    public void createTest() {
        Comment createdCommit = commentDao.create(comment);
        assertNotNull(createdCommit.getId());
        comment.setId(createdCommit.getId());
    }

    @Test
    @Order(2)
    public void readByIdTest() {
        Optional<Comment> comment = commentDao.readById(1);
        assertFalse(comment.isEmpty());
    }

    @Test
    @Order(3)
    public void readAllTest(){
        List<Comment> comments = commentDao.readAll();
        assertFalse(comments.isEmpty());
    }

    @Test
    @Order(4)
    public void updateTest(){
        String newCommentContent = "Boring place!";
        Comment commentFromDB = commentDao.readById(1).get();
        commentFromDB.setContent(newCommentContent);
        Comment updatedComment = commentDao.update(commentFromDB);
        assertEquals(updatedComment.getContent(), newCommentContent);
    }

    @Test
    @Order(5)
    public void deleteByIdTest(){
        Integer id = 1;
        commentDao.deleteById(id);
        Optional<Comment> comment = commentDao.readById(id);
        assertFalse(comment.isPresent());
    }

    @AfterAll
    public static void tearDown() {
        try {
            commentDao.getConnection().rollback();
        } catch (SQLException e) {
            CommentDao.logger.error(e);
        }
    }

}
