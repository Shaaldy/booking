package by.shaaldy.booking.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.shaaldy.booking.dto.request.booking.CreateBookingRequest;
import by.shaaldy.booking.dto.request.booking.UpdateBookingStatusRequest;
import by.shaaldy.booking.dto.response.BookingResponse;
import by.shaaldy.booking.entity.Booking;
import by.shaaldy.booking.entity.BookingStatus;
import by.shaaldy.booking.entity.Room;
import by.shaaldy.booking.entity.User;
import by.shaaldy.booking.exception.BusinessException;
import by.shaaldy.booking.exception.ResourceNotFoundException;
import by.shaaldy.booking.mapper.BookingMapper;
import by.shaaldy.booking.repository.BookingRepository;
import by.shaaldy.booking.repository.RoomRepository;
import by.shaaldy.booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
  private final BookingRepository bookingRepository;
  private final UserRepository userRepository;
  private final RoomRepository roomRepository;
  private final BookingMapper bookingMapper;

  @Transactional
  public BookingResponse create(CreateBookingRequest request) throws ResourceNotFoundException {
    log.info(
        "Creating booking for user: {}, room: {}, dates: {} to {}",
        request.getUserId(),
        request.getRoomId(),
        request.getCheckIn(),
        request.getCheckOut());

    User user =
        userRepository
            .findById(request.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User", "Id", request.getUserId()));
    Room room =
        roomRepository
            .findById(request.getRoomId())
            .orElseThrow(() -> new ResourceNotFoundException("Room", "Id", request.getRoomId()));

    if (!request.getCheckOut().isAfter(request.getCheckIn())) {
      throw new BusinessException("Check-out date must be after check-in date");
    }

    Booking booking = bookingMapper.toEntity(request);
    booking.setUser(user);
    booking.setRoom(room);
    booking.setStatus(BookingStatus.CREATED);

    Booking saved = bookingRepository.save(booking);
    return bookingMapper.toResponse(saved);
  }

  @Transactional(readOnly = true)
  public BookingResponse getById(Long id) throws ResourceNotFoundException {
    log.info("Fetching bookings with id: {}", id);
    Booking booking =
        bookingRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Booking", "Id", id));
    return bookingMapper.toResponse(booking);
  }

  @Transactional(readOnly = true)
  public List<BookingResponse> getByUserId(Long userId) throws ResourceNotFoundException {
    log.info("Fetching bookings for user: {}", userId);

    if (!userRepository.existsById(userId)) {
      throw new ResourceNotFoundException("User", "Id", userId);
    }
    List<BookingStatus> activeStatuses = List.of(BookingStatus.CREATED, BookingStatus.CONFIRMED);
    return bookingRepository.findByUserIdAndStatusIn(userId, activeStatuses).stream()
        .map(bookingMapper::toResponse)
        .collect(Collectors.toList());
  }

  @Transactional
  public BookingResponse updateStatus(Long id, UpdateBookingStatusRequest request)
      throws ResourceNotFoundException {
    log.info("Updating booking status: {}, new status: {}", id, request.getStatus());

    Booking booking =
        bookingRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Booking", "Id", id));

    validateStatusTransition(booking.getStatus(), request.getStatus());

    booking.setStatus(request.getStatus());
    Booking updated = bookingRepository.save(booking);

    log.info("Booking status updated to: {}", request.getStatus());

    return bookingMapper.toResponse(updated);
  }

  @Transactional
  public void cancel(Long id) throws ResourceNotFoundException {
    log.info("Cancelling booking with id: {}", id);
    Booking booking =
        bookingRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Booking", "Id", id));
    if (booking.getStatus() == BookingStatus.CANCELLED) {
      throw new BusinessException("Booking is already cancelled");
    }
    if (booking.getStatus() == BookingStatus.COMPLETED) {
      throw new BusinessException("Cannot cancel completed booking");
    }

    booking.setStatus(BookingStatus.CANCELLED);
    bookingRepository.save(booking);

    log.info("Booking cancelled with id: {}", id);
  }

  private void validateStatusTransition(BookingStatus currentStatus, BookingStatus newStatus)
      throws BusinessException {
    if (currentStatus == newStatus) {
      throw new BusinessException("Cannot transition to the same status");
    }

    switch (currentStatus) {
      case CREATED -> {
        if (newStatus != BookingStatus.CONFIRMED && newStatus != BookingStatus.CANCELLED) {
          throw new BusinessException("Cannot transition from CREATED to " + newStatus);
        }
      }
      case CONFIRMED -> {
        if (newStatus != BookingStatus.CANCELLED && newStatus != BookingStatus.COMPLETED) {
          throw new BusinessException("Cannot transition from CREATED to " + newStatus);
        }
      }
      case CANCELLED, COMPLETED ->
          throw new BusinessException("Cannot transition from CREATED to " + newStatus);

      default -> throw new BusinessException("Unknown stats: " + currentStatus);
    }
  }
}
