import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SqlSmokeTest {

    private static Connection connection;

    @BeforeAll
    static void setup() throws Exception {
        String dbHost = System.getenv("DB_HOST");
        String dbPort = System.getenv("DB_PORT");

        if (dbHost == null) dbHost = "localhost";
        if (dbPort == null) dbPort = "5432";

        String url = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/testdb";
        connection = DriverManager.getConnection(url, "qa", "qa");

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, name VARCHAR(50));");
        }
    }

    @Test
    void insertAndSelectTest() throws Exception {
        // 1. Вставляем данные (INSERT)
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("INSERT INTO users (name) VALUES ('Alex');");
        }

        // 2. Читаем данные (SELECT)
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name FROM users WHERE name = 'Alex';")) {

            // Проверяем, что запись нашлась
            if (rs.next()) {
                String name = rs.getString("name");
                assertEquals("Alex", name);
            } else {
                throw new AssertionError("Пользователь Alex не найден в БД!");
            }
        }
    }

    @AfterAll
    static void tearDown() throws Exception {
        // Убираем за собой (удаляем таблицу)
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE users;");
        }
        if (connection != null) connection.close();
    }
}