package by.shaaldy.booking.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelSearchRequest {
    private String city;
    private Double minRating;
    private Integer guests;
    private LocalDate checkIn;
    private LocalDate checkOut;
}