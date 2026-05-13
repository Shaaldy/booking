package by.shaaldy.booking.repository;

import by.shaaldy.booking.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query(value = """
            SELECT DISTINCT h.* FROM hotels h
            JOIN rooms r ON h.id = r.hotel_id
            WHERE h.city = COALESCE(:city, h.city)
              AND (:minRating IS NULL OR h.rating >= :minRating)
              AND r.capacity >= :guests
              AND NOT EXISTS (
                  SELECT 1 FROM bookings b
                  WHERE b.room_id = r.id
                    AND b.status = 'CREATED'
                    AND b.check_out > :checkIn
                    AND b.check_in < :checkOut
              )
            ORDER BY h.rating DESC
            """, nativeQuery = true)
    Page<Hotel> searchAvailable(
            @Param("city") String city,
            @Param("minRating") Double minRating,
            @Param("guests") Integer guests,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut,
            Pageable pageable
    );

    List<Hotel> findByCity(String city);
}