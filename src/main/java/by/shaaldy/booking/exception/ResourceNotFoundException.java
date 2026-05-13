package by.shaaldy.booking.exception;

public class ResourceNotFoundException extends Throwable {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Конструктор с информацией о ресурсе
     * Автоматически форматирует сообщение
     *
     * @param resourceName название ресурса (e.g., "Hotel", "Room")
     * @param fieldName имя поля (e.g., "id", "email")
     * @param fieldValue значение поля (e.g., 123, "user@example.com")
     *
     * Пример вывода: "Hotel not found with id: '1'"
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
    }
}
