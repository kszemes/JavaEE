package hu.andika.javaee.model.user;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.andika.javaee.utils.DatabaseInit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao {

    protected String create;
    protected String read;
    protected String readAll;
    protected String update;
    protected String delete;

    private static final DatabaseInit databaseInit = new DatabaseInit();
    private static final Connection connection = databaseInit.getConnection();

    public static Logger logger = LogManager.getLogger();

    public Connection getConnection(){
        return connection;
    }

    public UserDao() {
        this.create = "INSERT INTO user ";
        this.read = "SELECT * FROM user WHERE id = ?";
        this.readAll = "SELECT * FROM user";
        this.update = "UPDATE user SET ";
        this.delete = "DELETE FROM user WHERE id = ?";
    }

    public User create(User user) {
        try (PreparedStatement pst =
                     getConnection().prepareStatement(
                             String.format("%s(userName, password, role) values (?, ?, ?)", this.create), Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, user.getUserName());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getRole().toString());
            int affectedRow = pst.executeUpdate();
            if (affectedRow <= 0) {
                logger.error("SQL Insert failed!");
            }
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
            logger.info("The User successfully created: " + user + " with ID: " + user.getId());
            return user;
        } catch (SQLException e) {
            logger.catching(Level.ERROR, e);
        }
        return null;
    }

    public Optional<User> readById(Integer id) {
        try (PreparedStatement pst = getConnection().prepareStatement(this.read)) {
            User user = null;
            pst.setInt(1, id);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("userName"),
                        resultSet.getString("password"),
                        Role.valueOf(resultSet.getString("role"))
                );
            }
            logger.info(user.getId() == null ? "There is no User with ID: " + id : "User found: " + user);
            return Optional.of(user);
        } catch (Exception e) {
            logger.error("Exception is: " + e);
            logger.error("There is no User with ID: " + id);
        }
        return Optional.empty();
    }

    public List<User> readAll() {
        List<User> users = null;
        try (PreparedStatement pst = getConnection().prepareStatement(this.readAll)) {
            users = new ArrayList<>();
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                users.add(
                    new User(
                            resultSet.getInt("id"),
                            resultSet.getString("userName"),
                            resultSet.getString("password"),
                            Role.valueOf(resultSet.getString("role"))
                    )
                );
            }
            logger.info("Found " + users.size() + " Users from database");
            return users;
        } catch (Exception e) {
            logger.error("There is no Users in the table!");
        }
        return users;
    }

    public User update(User user) {
        try (PreparedStatement pst = getConnection().prepareStatement(
                String.format("%s userName = ?, password = ?, role = ? WHERE id = ?", this.update),
                Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, user.getUserName());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getRole().toString());
            pst.setInt(4, user.getId());
            int affectedRow = pst.executeUpdate();
            if (affectedRow <= 0) {
                logger.error("SQL Update failed!");
            }
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
            logger.info("The User updated: " + user);
            return user;
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
            logger.info("The User with ID: " + id + " was successfully deleted!");
        } catch (SQLException e) {
            logger.catching(Level.ERROR, e);
        }
    }

    public Optional<User> findByUserName(String userName){
        return null;
    }

    public Optional<User> checkLogin(String userName, String password) {
        return null;
    }
}