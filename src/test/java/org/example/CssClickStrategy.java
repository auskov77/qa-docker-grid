package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CssClickStrategy implements ClickStrategy {
    @Override
    public void clickLoginButton(WebDriver driver) {
        driver.findElement(By.cssSelector("input[type='submit']")).click();
    }
}