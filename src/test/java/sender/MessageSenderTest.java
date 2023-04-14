package sender;

import geo.GeoService;
import geo.GeoServiceImpl;
import localization.LocalizationService;
import localization.LocalizationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.HashMap;
import java.util.Map;

public class MessageSenderTest {

    GeoService geoService;
    LocalizationService localizationService;
    MessageSender sender;

    final String RUSSIAN_IP = "172.44.55.66";
    final String AMERICAN_IP = "96.44.55.66";
    final String ANOTHER_IP = "12.567.66";
    final String EMPTY_IP = "";


    @BeforeEach
    void init() {
        geoService = Mockito.spy(GeoServiceImpl.class);
        localizationService = Mockito.spy(LocalizationServiceImpl.class);
        sender = new MessageSenderImpl(geoService, localizationService);
    }


    @Test
    void send_russianIpTest() {

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, RUSSIAN_IP);

        String actual = sender.send(headers);
        Assertions.assertTrue(actual.matches(".*\\p{InCyrillic}.*"));
    }
    @Test
    void send_russianIpNotEverGreenTest() {

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, AMERICAN_IP);

        String actual = sender.send(headers);
        Assertions.assertTrue(actual.matches(".*\\p{InCyrillic}.*"));
    }

    @Test
    void send_americanIpTest() {

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, AMERICAN_IP);

        String actual = sender.send(headers);
        Assertions.assertFalse(actual.matches(".*\\p{InCyrillic}.*"));
    }

    @Test
    void send_americanIpNotEverGreenTest() {

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, RUSSIAN_IP);

        String actual = sender.send(headers);
        Assertions.assertFalse(actual.matches(".*\\p{InCyrillic}.*"));
    }

    @Test
    void send_anotherIpTest() {

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ANOTHER_IP);

        Assertions.assertThrows(NullPointerException.class, () -> sender.send(headers));
    }

    @Test
    void send_emptyIpTest() {

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, EMPTY_IP);

        String expected = "Welcome";
        String actual = sender.send(headers);
        Assertions.assertTrue(actual.matches(".*\\p{IsAlphabetic}.*"));
        Assertions.assertEquals(expected, actual);
    }

}
