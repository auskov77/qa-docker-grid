import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            createDriver();
        }
        return driverThreadLocal.get();
    }

    private static void createDriver() {
        try {
            ChromeOptions options = new ChromeOptions();

            // Магия: читаем переменную окружения GRID_URL
            String gridUrl = System.getenv("GRID_URL");

            // Если переменная не задана (мы в IDEA) — используем localhost
            if (gridUrl == null || gridUrl.isEmpty()) {
                gridUrl = "http://localhost:4444";
            }

            WebDriver driver = new RemoteWebDriver(new URL(gridUrl), options);
            driverThreadLocal.set(driver);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to create WebDriver", e);
        }
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }
}