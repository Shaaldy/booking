package by.shaaldy.booking.dto.request.hotel;

import jakarta.validation.constraints.AssertTrue;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelSearchRequest {
    private String city;
    private Double minRating;
    private Integer guests;
    private LocalDate checkIn;
    private LocalDate checkOut;

    @AssertTrue(message = "Check-out must be after check-in")
    public boolean isValidDateRange() {
        if (checkIn == null || checkOut == null) {
            return true;
        }
        return checkOut.isAfter(checkIn);
    }
}