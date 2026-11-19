import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Test
    void testUserService() throws Exception {

        //  1.Создай мок EmailService через mock(EmailService.class).
        EmailService mockEmailService  = mock(EmailService.class);

        //  2.Настрой его так, чтобы он бросал исключение:
        //      doThrow(new RuntimeException("SMTP down")).when(mockEmailService).sendEmail(anyString(), anyString());
        doThrow(new RuntimeException("SMTP down"))
                .when(mockEmailService)
                .sendEmail(anyString(), anyString());

        //  3.Создай UserService и передай в него мок.
        UserService userService = new UserService(mockEmailService);

        //  4.Вызови registerUser("test@test.com").
        boolean result = userService.registerUser("test@test.com");

        //  5.Проверь через assertFalse, что метод вернул false (то есть код не упал, а обработал ошибку).
        assertFalse(result);

        //  6.Проверь через verify, что sendEmail был вызван 1 раз.
        verify(mockEmailService, times(1)).sendEmail(eq("test@test.com"), anyString());

    }
}
