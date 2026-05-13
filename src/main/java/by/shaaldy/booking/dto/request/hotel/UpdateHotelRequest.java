package by.shaaldy.booking.dto.request.hotel;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateHotelRequest {
    private String name;
    private String city;
    private String address;
    private Double rating;
}
