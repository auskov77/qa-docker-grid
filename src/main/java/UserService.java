// 2. Сервис, который мы тестируем
public class UserService {
    private final EmailService emailService;

    public UserService(EmailService emailService) {
        this.emailService = emailService;
    }

    public boolean registerUser(String email) {
        try {
            emailService.sendEmail(email, "Welcome!");
            return true;
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            return false; // Обработали ошибку
        }
    }
}