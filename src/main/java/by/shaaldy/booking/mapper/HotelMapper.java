package by.shaaldy.booking.mapper;

import org.mapstruct.*;

import by.shaaldy.booking.dto.request.hotel.CreateHotelRequest;
import by.shaaldy.booking.dto.request.hotel.UpdateHotelRequest;
import by.shaaldy.booking.dto.response.HotelResponse;
import by.shaaldy.booking.entity.Hotel;

@Mapper(componentModel = "spring")
public interface HotelMapper {
  HotelResponse toResponse(Hotel hotel);

  Hotel toEntity(CreateHotelRequest request);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(UpdateHotelRequest request, @MappingTarget Hotel hotel);

  @Mapping(target = "availableRooms", ignore = true)
  HotelResponse toResponseWithoutRooms(Hotel hotel);
}
