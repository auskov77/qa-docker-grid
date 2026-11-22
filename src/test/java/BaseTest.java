import org.example.DriverManager;
import org.junit.jupiter.api.AfterEach;

public abstract class BaseTest {

    @AfterEach
    void tearDown() {
        // Каждый раз после теста гарантированно убиваем браузер и чистим ThreadLocal
        DriverManager.quitDriver();
    }
}