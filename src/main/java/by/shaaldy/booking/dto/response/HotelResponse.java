package by.shaaldy.booking.dto.response;

import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelResponse {
  private Long id;
  private String name;
  private String city;
  private Double rating;
  private List<RoomResponse> availableRooms;
}
