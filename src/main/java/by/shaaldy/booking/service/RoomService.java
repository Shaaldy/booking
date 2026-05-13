package by.shaaldy.booking.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.shaaldy.booking.dto.request.room.CreateRoomRequest;
import by.shaaldy.booking.dto.request.room.UpdateRoomRequest;
import by.shaaldy.booking.dto.response.RoomResponse;
import by.shaaldy.booking.entity.Hotel;
import by.shaaldy.booking.entity.Room;
import by.shaaldy.booking.exception.ResourceNotFoundException;
import by.shaaldy.booking.mapper.RoomMapper;
import by.shaaldy.booking.repository.HotelRepository;
import by.shaaldy.booking.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {
  private final RoomRepository roomRepository;
  private final HotelRepository hotelRepository;
  private final RoomMapper roomMapper;

  /** Создать новую комнату в отеле */
  @Transactional
  public RoomResponse create(CreateRoomRequest request) throws ResourceNotFoundException {
    log.info("Creating room in hotel: {} with type: {}", request.getHotelId(), request.getType());

    Hotel hotel =
        hotelRepository
            .findById(request.getHotelId())
            .orElseThrow(() -> new ResourceNotFoundException("Hotel", "id", request.getHotelId()));

    Room room = roomMapper.toEntity(request);
    room.setHotel(hotel);

    Room saved = roomRepository.save(room);
    log.info("Room created with id: {} in hotel: {}", saved.getId(), hotel.getId());

    return roomMapper.toResponse(saved);
  }

  /** Получить комнату по ID */
  @Transactional(readOnly = true)
  public RoomResponse getById(Long id) throws ResourceNotFoundException {
    log.info("Fetching room with id: {}", id);

    Room room =
        roomRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Room", "id", id));

    return roomMapper.toResponse(room);
  }

  /** Получить все комнаты отеля */
  @Transactional(readOnly = true)
  public List<RoomResponse> getByHotelId(Long hotelId) throws ResourceNotFoundException {
    log.info("Fetching rooms for hotel: {}", hotelId);

    // Проверить что отель существует
    if (!hotelRepository.existsById(hotelId)) {
      throw new ResourceNotFoundException("Hotel", "id", hotelId);
    }

    return roomRepository.findByHotelId(hotelId).stream()
        .map(roomMapper::toResponse)
        .collect(Collectors.toList());
  }

  /** Обновить комнату */
  @Transactional
  public RoomResponse update(Long id, UpdateRoomRequest request) throws ResourceNotFoundException {
    log.info("Updating room with id: {}", id);

    Room room =
        roomRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Room", "id", id));

    roomMapper.updateEntity(request, room);
    Room updated = roomRepository.save(room);

    log.info("Room updated with id: {}", id);
    return roomMapper.toResponse(updated);
  }

  /** Удалить комнату */
  @Transactional
  public void delete(Long id) throws ResourceNotFoundException {
    log.info("Deleting room with id: {}", id);

    Room room =
        roomRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Room", "id", id));

    roomRepository.delete(room);
    log.info("Room deleted with id: {}", id);
  }
}
