package by.shaaldy.booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResponse {
        private Long id;
        private String number;
        private String type;
        private BigDecimal pricePerNight;
        private Integer capacity;
}
