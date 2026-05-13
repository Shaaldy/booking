package by.shaaldy.booking.entity;

public enum BookingStatus {
  CREATED, // Создано
  CONFIRMED, // Подтверждено
  CANCELLED, // Отменено
  COMPLETED; // Завершено

  public boolean isActive() {
    return this == CREATED || this == CONFIRMED;
  }
}
