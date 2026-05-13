package by.shaaldy.booking.dto.response;

import lombok.*;

import java.util.List;

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
