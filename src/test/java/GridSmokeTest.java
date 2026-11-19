import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GridSmokeTest extends BaseTest {

    @Test
    void testOne() {
        WebDriver driver = DriverManager.getDriver();
        driver.get("https://example.com");
        assertTrue(driver.getTitle().contains("Example Domain"));
    }

    @Test
    void testTwo() {
        WebDriver driver = DriverManager.getDriver();
        driver.get("https://www.wikipedia.org");
        assertTrue(driver.getTitle().contains("Wikipedia"));
    }

    @Test
    void testThree() {
        WebDriver driver = DriverManager.getDriver();
        driver.get("https://www.google.com");
        assertTrue(driver.getTitle().contains("Google"));
    }

    @Test
    void testFour() {
        WebDriver driver = DriverManager.getDriver();
        driver.get("https://github.com");
        assertEquals("https://github.com/", driver.getCurrentUrl());
    }
}