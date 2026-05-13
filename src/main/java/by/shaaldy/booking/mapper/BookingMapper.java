package by.shaaldy.booking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import by.shaaldy.booking.dto.request.booking.CreateBookingRequest;
import by.shaaldy.booking.dto.response.BookingResponse;
import by.shaaldy.booking.entity.Booking;

@Mapper(componentModel = "spring")
public interface BookingMapper {

  @Mapping(source = "user.id", target = "userId")
  @Mapping(source = "room.id", target = "roomId")
  BookingResponse toResponse(Booking booking);

  @Mapping(target = "user", ignore = true)
  @Mapping(target = "room", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  Booking toEntity(CreateBookingRequest request);
}
