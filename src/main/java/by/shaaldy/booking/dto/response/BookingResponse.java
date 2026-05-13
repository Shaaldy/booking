package by.shaaldy.booking.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import by.shaaldy.booking.entity.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {
  private Long id;
  private Long userId;
  private Long roomId;
  private LocalDate checkIn;
  private LocalDate checkOut;
  private BookingStatus status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
