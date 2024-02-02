package hu.andika.javaee.model.user;

import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDaoTest {

    private static UserDao userDao;
    private static final User user = new User("testuser", "testuser", Role.REGULARUSER);

    @BeforeAll
    public static void setup() throws SQLException {
        userDao = new UserDao();
        userDao.getConnection().setAutoCommit(false);
    }

    @Test
    @Order(1)
    public void createTest() {
        User createdUser = userDao.create(user);
        assertNotNull(createdUser.getId());
        user.setId(createdUser.getId());
    }

    @Test
    @Order(2)
    public void readByIdTest() {
        Optional<User> user = userDao.readById(1);
        assertFalse(user.isEmpty());
    }

    @Test
    @Order(3)
    public void readAllTest(){
        List<User> users = userDao.readAll();
        assertFalse(users.isEmpty());
    }

    @Test
    @Order(4)
    public void updateTest(){
        String newUserName = "Andikacska";
        User userFromDB = userDao.readById(1).get();
        userFromDB.setUserName(newUserName);
        User updatedUser = userDao.update(userFromDB);
        assertEquals(updatedUser.getUserName(), newUserName);
    }

    @Test
    @Order(5)
    public void deleteByIdTest(){
        Integer id = 1;
        userDao.deleteById(id);
        Optional<User> user = userDao.readById(id);
        assertFalse(user.isPresent());
    }

    @AfterAll
    public static void tearDown() {
        try {
            userDao.getConnection().rollback();
        } catch (SQLException e) {
            UserDao.logger.error(e);
        }
    }

}
