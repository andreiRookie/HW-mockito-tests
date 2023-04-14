package localization;

import entity.Country;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class LocalizationServiceTest {

    LocalizationService service;

    @BeforeEach
    void init() {
        service = new LocalizationServiceImpl();
    }

    @ParameterizedTest
    @EnumSource(mode = EnumSource.Mode.EXCLUDE, names = {"RUSSIA"})
    void locale_excludeRussiaTest(Country country) {

        String expected = "Welcome";
        String actual = service.locale(country);

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @EnumSource(mode = EnumSource.Mode.EXCLUDE, names = {"GERMANY","USA", "BRAZIL"})
    void locale_includeOnlyRussiaTest(Country country) {
        String expected = "Добро пожаловать";
        String actual = service.locale(country);

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @EnumSource(mode = EnumSource.Mode.EXCLUDE, names = {"GERMANY","USA"})
    void locale_includeOnlyRussiaFailedTest(Country country) {
        String expected = "Добро пожаловать";
        String actual = service.locale(country);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void locale_defaultTest() {
        Country country1 = Country.BRAZIL;
        Country country2 = Country.USA;
        Country country3 = Country.GERMANY;
        String expected = "Welcome";

        String actual1 = service.locale(country1);
        String actual2 = service.locale(country2);
        String actual3 = service.locale(country3);

        Assertions.assertEquals(expected, actual1);
        Assertions.assertEquals(expected, actual2);
        Assertions.assertEquals(expected, actual3);
    }

    @Test
    void locale_notEverGreenDefaultTest() {
        Country country = Country.BRAZIL;
        String expected = "Добро пожаловать";

        String actual = service.locale(country);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void locale_russianTest() {
        Country country = Country.RUSSIA;

        String expected = "Добро пожаловать";
        String actual = service.locale(country);

        Assertions.assertEquals(expected, actual);
    }

}
