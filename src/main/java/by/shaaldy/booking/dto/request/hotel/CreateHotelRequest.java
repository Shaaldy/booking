package by.shaaldy.booking.dto.request.hotel;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateHotelRequest {
  private String name;
  private String city;
  private String address;
  private Double rating;
}
