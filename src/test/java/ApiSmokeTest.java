import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiSmokeTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    // проверяем GET /api/users/{id}
    @Test
    void getUserByIdTest() {
        given()
                // .header("x-api-key", "reqres-free-v1") // Если потребуется ключ
                .when()
                .get("/users/2")
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.email", equalTo("janet.weaver@reqres.in"));
    }

    // проверяем GET /users/999 (пользователь не существует). Ожидай код 404
    @Test
    void getUserDoesNotExist() {
        given()
                .when()
                .get("/users/999")
                .then()
                .statusCode(404);
    }
    // тест для проверки невалидного эндпоинта: GET /users/abc. Ожидай код 400 (или 404, как сервер ответит)
    @Test
    void getInvalidEndpoint() {
        given()
                .when()
                .get("/users/abc")
                .then()
                .statusCode(404);
    }

//    тест, который://
//    1. Логинится (или регистрируется), чтобы получить токен.
//    2. Создает нового пользователя.
//    3. Проверяет, что пользователь создан.
    @Test
    void createUserTest() {
        // 1. Подготовка тела запроса (JSON)
        String requestBody = """
            {
                "name": "Alex",
                "job": "QA Engineer"
            }
            """;

        io.restassured.response.Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract().response();

        String userId = response.jsonPath().getString("id");
        System.out.println("Создан пользователь с ID: " + userId);
    }
}