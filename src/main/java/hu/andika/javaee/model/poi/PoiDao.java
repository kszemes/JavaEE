package hu.andika.javaee.model.poi;

import hu.andika.javaee.utils.DatabaseInit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class PoiDao {

    protected String create;
    protected String read;
    protected String readAll;
    protected String update;
    protected String delete;

    private static final DatabaseInit databaseInit = new DatabaseInit();
    private static final Connection connection = databaseInit.getConnection();

    public static Logger logger = LogManager.getLogger();

    public Connection getConnection() {
        return connection;
    }

    public PoiDao() {
        this.create = "INSERT INTO poi ";
        this.read = "SELECT * FROM poi WHERE id = ?";
        this.readAll = "SELECT * FROM poi";
        this.update = "UPDATE poi SET ";
        this.delete = "DELETE FROM poi WHERE id = ?";
    }

    public Poi create(Poi poi) {
        try (PreparedStatement pst =
                     getConnection().prepareStatement(
                             String.format("%s(name, location, type, likes) values (?, ?, ?, ?)", this.create), Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, poi.getName());
            pst.setString(2, poi.getLocation());
            pst.setString(3, poi.getType());
            pst.setInt(4, poi.getLikes());
            int affectedRow = pst.executeUpdate();
            if (affectedRow <= 0) {
                logger.error("SQL Insert failed!");
            }
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                poi.setId(generatedKeys.getInt(1));
            }
            logger.info("The Poi successfully created: " + poi + " with ID: " + poi.getId());
            return poi;
        } catch (SQLException e) {
            logger.catching(Level.ERROR, e);
        }
        return null;
    }

    public Optional<Poi> readById(Integer id) {
        try (PreparedStatement pst = getConnection().prepareStatement(this.read)) {
            Poi poi = null;
            pst.setInt(1, id);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                poi = new Poi(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("location"),
                        resultSet.getString("type"),
                        resultSet.getInt("likes")
                );
            }
            logger.info(poi.getId() == null ? "There is no Pio with ID: " + id : "Poi found: " + poi);
            return Optional.of(poi);
        } catch (Exception e) {
            logger.error("Exception is: " + e);
            logger.error("There is no User with ID: " + id);
        }
        return Optional.empty();
    }

    public List<Poi> readAll() {
        List<Poi> pois = null;
        try (PreparedStatement pst = getConnection().prepareStatement(this.readAll)) {
            pois = new ArrayList<>();
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                pois.add(
                        new Poi(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("location"),
                                resultSet.getString("type"),
                                resultSet.getInt("likes")
                        )
                );
            }
            logger.info("Found " + pois.size() + " Pois from database");
            return pois;
        } catch (Exception e) {
            logger.error("There is no Pois in the table!");
        }
        return pois;
    }

    public Poi update(Poi poi) {
        try (PreparedStatement pst = getConnection().prepareStatement(
                String.format("%s name = ?, location = ?, type = ?, likes = ? WHERE id = ?", this.update),
                Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, poi.getName());
            pst.setString(2, poi.getLocation());
            pst.setString(3, poi.getType());
            pst.setInt(4, poi.getLikes());
            pst.setInt(5, poi.getId());
            int affectedRow = pst.executeUpdate();
            if (affectedRow <= 0) {
                logger.error("SQL Update failed!");
            }
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                poi.setId(generatedKeys.getInt(1));
            }
            logger.info("The Poi updated: " + poi);
            return poi;
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
            logger.info("The Poi with ID: " + id + " was successfully deleted!");
        } catch (SQLException e) {
            logger.catching(Level.ERROR, e);
        }
    }

    public Set<String> getLocations() {
        Set<String> locations = new HashSet<>();
        try (PreparedStatement pst = getConnection().prepareStatement("select location from poi group by location order by location")) {
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                locations.add(resultSet.getString("location"));
            }
        } catch (SQLException e) {
            logger.catching(Level.ERROR, e);
        }
        logger.info("Found " + locations.size() + " pieces of locations.");
        return locations;
    }

    public Set<String> getTypes() {
        Set<String> types = new HashSet<>();
        try (PreparedStatement pst = getConnection().prepareStatement("select type from poi group by type order by type")) {
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                types.add(resultSet.getString("type"));
            }
        } catch (SQLException e) {
            logger.catching(Level.ERROR, e);
        }
        logger.info("Found " + types.size() + " pieces of types.");
        return types;
    }

    public List<Poi> getPoisByLocation(String location) {
        List<Poi> pois = new ArrayList<>();
        try (PreparedStatement pst = getConnection().prepareStatement("select * from main.poi where location like ?")) {
            pst.setString(1, "%" + location + "%");
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                pois.add(
                    new Poi(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("location"),
                        resultSet.getString("type"),
                        resultSet.getInt("likes")
                    )
                );
            }
            logger.info("Found " + pois.size() + " Pois from database by location: " + location);
            return pois;
        } catch (Exception e) {
            logger.error("Exception is: " + e);
            logger.error("There is no Pois in the table with location: " + location);
        }
        return pois;
    }

    public List<Poi> getPoisByType(String type) {
        List<Poi> pois = new ArrayList<>();
        try (PreparedStatement pst = getConnection().prepareStatement("select * from main.poi where type like ?")) {
            pst.setString(1, "%" + type + "%");
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                pois.add(
                    new Poi(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("type"),
                        resultSet.getString("type"),
                        resultSet.getInt("likes")
                    )
                );
            }
            logger.info("Found " + pois.size() + " Pois from database by type: " + type);
            return pois;
        } catch (Exception e) {
            logger.error("Exception is: " + e);
            logger.error("There is no Pois in the table with type: " + type);
        }
        return pois;
    }
}