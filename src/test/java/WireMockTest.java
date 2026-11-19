import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WireMockTest {

    private WireMockServer wireMockServer;

    @BeforeEach
    void setup() {
        // 1. Запускаем WireMock на порту 8089
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
    }

    @AfterEach
    void tearDown() {
        // 2. Останавливаем сервер после теста
        wireMockServer.stop();
    }

    @Test
    void testFakePaymentService() throws Exception {
        // 3. Настраиваем заглушку (Stub): 
        // Если придет GET /api/payment/123, ответить 200 OK и JSON
        wireMockServer.stubFor(get(urlEqualTo("/api/payment/123"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"status\": \"SUCCESS\", \"amount\": 100}")));

        // 4. Делаем реальный HTTP-запрос на наш фейковый сервер
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8089/api/payment/123"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 5. Проверяем, что получили ответ от WireMock
        assertEquals(200, response.statusCode());
        assertEquals("{\"status\": \"SUCCESS\", \"amount\": 100}", response.body());

        // 6. Проверяем, что запрос действительно был
        wireMockServer.verify(getRequestedFor(urlEqualTo("/api/payment/123")));

        // 7. Имитируем падение сервиса
        wireMockServer.stubFor(get(urlEqualTo("/api/payment/123"))
                .willReturn(aResponse().withStatus(500)));
    }
}