package by.shaaldy.booking.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateHotelRequest {
    private String name;
    private String city;
    private String address;
    private Double rating;
}
