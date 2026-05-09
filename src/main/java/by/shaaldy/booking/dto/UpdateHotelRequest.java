package by.shaaldy.booking.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateHotelRequest {
    private String name;
    private String city;
    private Double rating;
}
