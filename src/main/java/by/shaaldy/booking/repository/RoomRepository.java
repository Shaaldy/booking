package by.shaaldy.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.shaaldy.booking.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

  List<Room> findByHotelId(Long hotelId);

  boolean existsByIdAndHotelId(Long roomId, Long hotelId);
}
