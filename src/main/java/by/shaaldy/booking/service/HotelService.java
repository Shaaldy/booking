package by.shaaldy.booking.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.shaaldy.booking.dto.request.hotel.CreateHotelRequest;
import by.shaaldy.booking.dto.request.hotel.UpdateHotelRequest;
import by.shaaldy.booking.dto.response.HotelResponse;
import by.shaaldy.booking.entity.Hotel;
import by.shaaldy.booking.exception.ResourceNotFoundException;
import by.shaaldy.booking.mapper.HotelMapper;
import by.shaaldy.booking.mapper.RoomMapper;
import by.shaaldy.booking.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelService {
  private final HotelRepository hotelRepository;
  private final HotelMapper hotelMapper;
  private final RoomMapper roomMapper;

  @Transactional
  public HotelResponse create(CreateHotelRequest request) {
    log.info("Creating new hotel: {}", request.getName());

    Hotel hotel = hotelMapper.toEntity(request);
    Hotel saved = hotelRepository.save(hotel);

    log.info("Hotel created with id: {}", saved.getId());
    return hotelMapper.toResponseWithoutRooms(saved);
  }

  @Transactional(readOnly = true)
  public HotelResponse getById(Long id) throws ResourceNotFoundException {
    log.info("Fetching hotel with id: {}", id);

    Hotel hotel =
        hotelRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel", "id", id));

    return hotelMapper.toResponse(hotel);
  }

  @Transactional(readOnly = true)
  public Page<HotelResponse> getAll(Pageable pageable) {
    log.info("Fetching all hotels, page: {}", pageable.getPageNumber());

    return hotelRepository.findAll(pageable).map(hotelMapper::toResponseWithoutRooms);
  }

  @Transactional
  public HotelResponse update(Long id, UpdateHotelRequest request)
      throws ResourceNotFoundException {
    log.info("Updating hotel with id: {}", id);
    Hotel hotel =
        hotelRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel", "id", id));

    hotelMapper.updateEntity(request, hotel);

    Hotel updated = hotelRepository.save(hotel);
    log.info("Hotel updated with id: {}", id);

    return hotelMapper.toResponseWithoutRooms(updated);
  }

  @Transactional
  public void delete(Long id) throws ResourceNotFoundException {
    log.info("Deleting hotel with id: {}", id);
    Hotel hotel =
        hotelRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel", "id", id));
    hotelRepository.delete(hotel);
    log.info("Hotel deleted with id: {}", id);
  }
}
