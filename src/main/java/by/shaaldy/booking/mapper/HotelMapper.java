package by.shaaldy.booking.mapper;

import org.mapstruct.*;

import by.shaaldy.booking.dto.request.hotel.CreateHotelRequest;
import by.shaaldy.booking.dto.request.hotel.UpdateHotelRequest;
import by.shaaldy.booking.dto.response.HotelResponse;
import by.shaaldy.booking.entity.Hotel;

@Mapper(componentModel = "spring", uses = RoomMapper.class)
public interface HotelMapper {

  /** Преобразовать Entity в Response с доступными комнатами */
  @Mapping(target = "availableRooms", ignore = true)
  HotelResponse toResponse(Hotel hotel);

  /** Преобразовать CreateRequest в Entity */
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "rooms", ignore = true)
  Hotel toEntity(CreateHotelRequest request);

  /** Обновить Entity значениями из UpdateRequest */
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "rooms", ignore = true)
  void updateEntity(UpdateHotelRequest request, @MappingTarget Hotel hotel);

  /** Создать response без комнат (для GET /hotels/{id}) */
  @Mapping(target = "availableRooms", ignore = true)
  HotelResponse toResponseWithoutRooms(Hotel hotel);
}
