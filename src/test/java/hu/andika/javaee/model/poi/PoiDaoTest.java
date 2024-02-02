package hu.andika.javaee.model.poi;

import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PoiDaoTest {

    private static PoiDao poidao;
    private static final Poi poi = new Poi("Pizza Hut", "London", "Restarurant", 0);

    @BeforeAll
    public static void setup() throws SQLException {
        poidao = new PoiDao();
        poidao.getConnection().setAutoCommit(false);
    }

    @Test
    @Order(1)
    public void createTest() {
        Poi createdPoi = poidao.create(poi);
        assertNotNull(createdPoi.getId());
        poi.setId(createdPoi.getId());
    }

    @Test
    @Order(2)
    public void readByIdTest() {
        Optional<Poi> poi = poidao.readById(1);
        assertFalse(poi.isEmpty());
    }

    @Test
    @Order(3)
    public void readAllTest(){
        List<Poi> pois = poidao.readAll();
        assertFalse(pois.isEmpty());
    }

    @Test
    @Order(4)
    public void updateTest(){
        String newPoiName = "Belfry";
        Poi poiFromDB = poidao.readById(1).get();
        poiFromDB.setName(newPoiName);
        Poi updatedPoi = poidao.update(poiFromDB);
        assertEquals(updatedPoi.getName(), newPoiName);
    }

    @Test
    @Order(5)
    public void deleteByIdTest(){
        Integer id = 1;
        poidao.deleteById(id);
        Optional<Poi> poi = poidao.readById(id);
        assertFalse(poi.isPresent());
    }

    @AfterAll
    public static void tearDown() {
        try {
            poidao.getConnection().rollback();
        } catch (SQLException e) {
            PoiDao.logger.error(e);
        }
    }

}
