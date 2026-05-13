package by.shaaldy.booking.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.shaaldy.booking.entity.Booking;
import by.shaaldy.booking.entity.BookingStatus;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
  List<Booking> findByUserIdAndStatusIn(Long userId, List<BookingStatus> statuses);

  List<Booking> findByRoomIdAndStatusAndCheckOutAfterAndCheckInBefore(
      Long roomId, BookingStatus status, LocalDate checkOut, LocalDate checkIn);
}
