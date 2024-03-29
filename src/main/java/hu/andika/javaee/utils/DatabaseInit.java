package hu.andika.javaee.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseInit {

    private Connection connection;

    public DatabaseInit() {
        Logger logger = LogManager.getLogger();
        try {
            Class.forName("org.sqlite.JDBC");
            ClassLoader classLoader = getClass().getClassLoader();
            String resourcePath = "pois.db";
            java.net.URL resourceUrl = classLoader.getResource(resourcePath);
            String sqliteUrl = "jdbc:sqlite:" + resourceUrl.getFile();
            this.connection = DriverManager.getConnection(sqliteUrl);
            logger.info("Database Connection Successfull!");
        } catch (SQLException e) {
            logger.fatal("SQL Connection failed with the provided values!");
            logger.catching(Level.FATAL, e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}