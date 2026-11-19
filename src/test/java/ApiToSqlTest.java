import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiToSqlTest {

    private static Connection connection;

    @BeforeAll
    static void setup() throws Exception {
        String dbHost = System.getenv("DB_HOST");
        String dbPort = System.getenv("DB_PORT");

        if (dbHost == null) dbHost = "localhost";
        if (dbPort == null) dbPort = "5432";

        String url = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/testdb";
        connection = DriverManager.getConnection(url, "qa", "qa");

        // ДОБАВЛЯЕМ ЭТО: Создаем таблицу, если её нет
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
            CREATE TABLE IF NOT EXISTS api_users (
                id SERIAL PRIMARY KEY,
                external_id VARCHAR(50),
                name VARCHAR(50),
                job VARCHAR(50)
            );
        """);
        }
    }

    @Test
    void insertAndSelectTest() throws Exception {
        // 1. Создаем пользователя через API
        String requestBody = """
                {
                    "name": "Alex",
                    "job": "QA"
                }
                """;

        io.restassured.response.Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .extract().response();

        String userId = response.jsonPath().getString("id");
        String userName = response.jsonPath().getString("name");
        System.out.println("Создан пользователь: ID = " + userId + ", Name = " + userName + " в базе reqres.in");

        // 2. Вставляем в БД через PreparedStatement
        String insertSql = "INSERT INTO api_users (external_id, name) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSql)) {
            pstmt.setString(1, userId);
            pstmt.setString(2, userName);
            pstmt.executeUpdate(); // Выполняем!
            System.out.println("Пользователь с данными из reqres.in добавлен в БД testdb");
        }

        // 3. Проверяем, что запись есть
        String selectSql = "SELECT external_id, name FROM api_users WHERE name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSql)) {
            pstmt.setString(1, userName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    assertEquals(userName, rs.getString("name"));
                    assertEquals(userId, rs.getString("external_id"));
                    System.out.println("Пользователь с ID = " + userId + ", Name = " + userName + " в testdb найден");
                } else {
                    throw new AssertionError("Пользователь " + userName + " не найден в БД!");
                }
            }
        }
    }

    @AfterAll
    static void tearDown() throws Exception {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS api_users;"); // IF EXISTS спасает от падения
        }
        if (connection != null) connection.close();
    }
}