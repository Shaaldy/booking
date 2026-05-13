package by.shaaldy.booking.dto.request.booking;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookingRequest {
    private Long userId;
    private Long roomId;

    @FutureOrPresent(message = "Check-in must be today or later")
    private LocalDate checkIn;

    @FutureOrPresent(message = "Check-out must be today or later")
    private LocalDate checkOut;

    @AssertTrue(message = "Check-out must be after check-in")
    public boolean isValidDateRange() {
        if (checkIn == null || checkOut == null) {
            return true;
        }
        return checkOut.isAfter(checkIn);
    }
}
