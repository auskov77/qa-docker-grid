// 1. Интерфейс почтового сервиса
public interface EmailService {
    void sendEmail(String to, String text) throws Exception;
}