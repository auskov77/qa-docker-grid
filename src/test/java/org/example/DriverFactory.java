package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;


import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;

public class DriverFactory {

    public static WebDriver createDriver(String browserName) {

        // ... внутри метода createDriver ...
        ChromeOptions options = new ChromeOptions();
        options.setCapability("se:timeout", 30000); // 30 секунд на создание сессии
        options.setCapability("se:connectTimeout", 30000); // 30 секунд на подключение

        String gridUrl = System.getenv("GRID_URL");
        if (gridUrl == null || gridUrl.isEmpty()) {
            gridUrl = "http://localhost:4444";
        }

        try {
            if (browserName.equalsIgnoreCase("chrome")) {
                return new RemoteWebDriver(new URL(gridUrl), new ChromeOptions());
            } else if (browserName.equalsIgnoreCase("firefox")) {
                return new RemoteWebDriver(new URL(gridUrl), new FirefoxOptions());
            } else {
                throw new IllegalArgumentException("Неизвестный браузер: " + browserName);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Ошибка при подключении к Grid", e);
        }
    }
}