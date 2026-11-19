import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MockitoTest {

    @Test
    void testMockitoBasics() {
        // 1. Создаем мок (заглушку) для интерфейса List
        List<String> mockedList = mock(List.class);

        // 2. Настраиваем поведение (Stubbing): когда вызовут get(0), вернуть "Hello"
        when(mockedList.get(0)).thenReturn("Hello");

        // 3. Вызываем метод у мока
        String result = mockedList.get(0);

        // 4. Проверяем, что вернулось ожидаемое значение
        assertEquals("Hello", result);

        // 5. Проверяем поведение (Verification): был ли вызван метод get(0) ровно 1 раз?
        verify(mockedList, times(1)).get(0);

        // 6. Проверяем, что метод get(1) вообще не вызывался
        verify(mockedList, never()).get(1);
    }
}