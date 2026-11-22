import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.stream.Stream;

//Задача: Есть список пользователей (класс User с полями name и age).
//       1. Отфильтруй пользователей старше 18 лет - делается с помощью метода filter()
//       2. Преобразуй их имена в верхний регистр - Используй метод .map(),
//                      чтобы взять имя пользователя (user.getName()) и сделать его заглавными буквами (toUpperCase()).
//       3. Отсортируй по возрасту - Используй метод .sorted(), чтобы отсортировать по возрасту.
//       4. Собери в список - Используй метод .collect(Collectors.toList()),
//                      чтобы вернуть результат в виде списка
//       5. и выведи на экран
public class StreamTest {
    static class User {
        String name;
        int age;
        public User(String name, int age) { this.name = name; this.age = age; }
        public String getName() { return name; }
        public int getAge() { return age; }
    }

    public static void main(String[] args) {
        List<User> users = Arrays.asList(
                new User("Alex", 25),
                new User("Bob", 17),
                new User("Charlie", 30),
                new User("Dave", 15)
        );

        // ТВОЙ КОД ЗДЕСЬ:
        // Используй users.stream()... и напиши цепочку
        // Результат выведи через System.out.println

        List<String> listResult =
                users.stream().
                        filter(user -> user.getAge() > 18).
                        map(user -> user.getName().toUpperCase()).
                        sorted().collect(Collectors.toList());

        System.out.println(listResult);

        List<String> orderIds = Arrays.asList("ORD-123", "ORD-456", "ORD-789");

//        Тебе нужно проверить, что среди них есть заказ с ID "ORD-456".
//        1. Напиши с использованием Streams однострочную проверку
//        (можно использовать anyMatch), которая возвращает true, если такой заказ есть. И добавь assertTrue(...).

        assertTrue(orderIds.stream().anyMatch(id -> id.equals("ORD-456")),
                "Заказ ORD-456 не найден!");
        System.out.println("Тест пройден: заказ найден!");
    }
}