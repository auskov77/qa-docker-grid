package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class IdClickStrategy implements ClickStrategy {
    @Override
    public void clickLoginButton(WebDriver driver) {
        driver.findElement(By.id("login-button")).click();
    }
}