package by.shaaldy.booking.dto.request.room;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRoomRequest {
  private String number;
  private String type;
  private BigDecimal pricePerNight;
  private Integer capacity;
}
