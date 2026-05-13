package by.shaaldy.booking.dto.request.room;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRoomRequest {
    private Long hotelId;
    private String number;
    private String type;
    private BigDecimal pricePerNight;
    private Integer capacity;
}
