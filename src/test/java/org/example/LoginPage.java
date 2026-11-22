package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private final WebDriver driver;
    private final ClickStrategy clickStrategy; // <--- Добавляем стратегию

    // Локаторы для полей ввода
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        // По умолчанию используем поиск по ID
        this.clickStrategy = new IdClickStrategy();
    }

    public void open() {
        driver.get("https://www.saucedemo.com/");
    }

    public void enterCredentials(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        // Делегируем клик стратегии
        clickStrategy.clickLoginButton(driver);
    }

    public String getPageTitleOrError() {
        if (!driver.findElements(By.className("title")).isEmpty()) {
            return driver.findElement(By.className("title")).getText();
        } else if (!driver.findElements(By.cssSelector("h3[data-test='error']")).isEmpty()) {
            return driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        }
        return "";
    }
}