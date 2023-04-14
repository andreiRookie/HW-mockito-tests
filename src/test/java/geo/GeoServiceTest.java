package geo;

import entity.Country;
import entity.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class GeoServiceTest {

    GeoService service;

    @BeforeEach
    void init() {
        service = new GeoServiceImpl();
    }

    @ParameterizedTest
    @ValueSource(strings = {"172.11","172.2.11", "172.0.32"})
    void byIp_russianIpsTest(String ip){
        Country expected = Country.RUSSIA;
        Location actual = service.byIp(ip);
        Assertions.assertEquals(expected, actual.getCountry());
    }


    @ParameterizedTest
    @ValueSource(strings = {"172.11","172.2.11", "172.0.32"})
    void byIp_moscowIpsTest(String ip){
        String expected = "Moscow";
        Location actual = service.byIp(ip);
        Assertions.assertTrue(expected.equalsIgnoreCase(actual.getCity()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"172.11","172.2.11", "172.0.32"})
    void byIp_moCSowIpsNotEverGreenTest(String ip){
        String expected = "Mocsow";
        Location actual = service.byIp(ip);
        Assertions.assertTrue(expected.equalsIgnoreCase(actual.getCity()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"172.11","172.2.11", "171.0.32"})
    void byIp_moscowIpsNotEverGreenTest(String ip){
        String expected = "Moscow";
        Location actual = service.byIp(ip);
        Assertions.assertTrue(expected.equalsIgnoreCase(actual.getCity()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"96.44.183.149","96.4.183.149", "96.44.183.1"})
    void byIp_americanIpsTest(String ip){
        Country expected = Country.USA;
        Location actual = service.byIp(ip);
        Assertions.assertEquals(expected, actual.getCountry());
    }

    @ParameterizedTest
    @ValueSource(strings = {"96.44.183.149","96.4.183.149", "96.44.183.1"})
    void byIp_newYorkIpsTest(String ip){
        String expected = "New York";
        Location actual = service.byIp(ip);
        Assertions.assertTrue(expected.equalsIgnoreCase(actual.getCity()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"96.44.183.149","96.4.183.149", "95.44.183.1"})
    void byIp_newYorkWrongIpFailedTest(String ip){
        String expected = "New York";
        Location actual = service.byIp(ip);
        Assertions.assertTrue(expected.equalsIgnoreCase(actual.getCity()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"44.183.149","83.149.44", "95.44.183.1"})
    void byIp_nullDefaultTest(String ip){
        Location actual = service.byIp(ip);
        Assertions.assertEquals(null, actual);
    }

    @Test
    void byCoordinates_shouldThrowTest(){
        double latitude = 22.35353;
        double longitude = 55.46346;
        Assertions.assertThrows(RuntimeException.class, () ->service.byCoordinates(latitude,longitude));
    }
}
