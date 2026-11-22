package org.example;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSteps {

    private WebDriver driver;
    private LoginPage loginPage; // <--- Добавляем Page Object

    @Given("я открываю страницу логина")
    public void openLoginPage() {
        // Получаем драйвер (Singleton позже)
        driver = DriverManager.getDriver();
        // Создаем Page Object, передавая ему драйвер
        loginPage = new LoginPage(driver);
        // Вызываем метод открытия страницы
        loginPage.open();
    }

    @When("я ввожу логин {string} и пароль {string}")
    public void enterCredentials(String username, String password) {
        // Делегируем ввод данных Page Object'у
        loginPage.enterCredentials(username, password);
    }

    @And("я нажимаю кнопку {string}")
    public void clickButton(String buttonName) {
        if (buttonName.equals("Login")) {
            // Делегируем клик Page Object'у
            loginPage.clickLogin();
        }
    }

    @Then("я должен увидеть заголовок {string}")
    public void verifyTitle(String expectedTitle) {
        // Ждем немного для загрузки страницы (можно улучшить, используя WebDriverWait)
        try { Thread.sleep(2000); } catch (InterruptedException e) {
            // Восстанавливаем флаг прерывания (Best Practice)
            Thread.currentThread().interrupt();
            throw new RuntimeException("Тест был прерван", e);
        }

        // Получаем текст через метод Page Object'а
        String actualText = loginPage.getPageTitleOrError();
        System.out.println("Найденный текст: " + actualText);

        assertTrue(actualText.contains(expectedTitle),
                "Ожидали: '" + expectedTitle + "', но получили: '" + actualText + "'");
    }
}