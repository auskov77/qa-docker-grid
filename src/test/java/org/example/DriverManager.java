package org.example;

import org.openqa.selenium.WebDriver;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final String DEFAULT_BROWSER = "chrome"; // Браузер по умолчанию

    private DriverManager() {}

    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            String browser = System.getenv("BROWSER");
            if (browser == null || browser.isEmpty()) {
                browser = DEFAULT_BROWSER;
            }
            System.out.println("Создаем драйвер для браузера: " + browser); // <--- ЛОГ
            WebDriver driver = DriverFactory.createDriver(browser);
            driverThreadLocal.set(driver);
        }
        return driverThreadLocal.get();
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }
}