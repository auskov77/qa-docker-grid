Feature: Логин на SauceDemo

  Scenario Outline: Вход с разными пользователями
    Given я открываю страницу логина
    When я ввожу логин "<username>" и пароль "<password>"
    And я нажимаю кнопку "Login"
    Then я должен увидеть заголовок "<expectedTitle>"

    Examples:
      | username       | password     | expectedTitle      |
      | standard_user  | secret_sauce | Products           |
      | locked_out_user| secret_sauce | Epic sadface: Sorry, this user has been locked out. |