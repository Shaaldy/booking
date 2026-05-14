package by.shaaldy.booking.mapper;

import org.mapstruct.*;

import by.shaaldy.booking.dto.request.room.CreateRoomRequest;
import by.shaaldy.booking.dto.request.room.UpdateRoomRequest;
import by.shaaldy.booking.dto.response.RoomResponse;
import by.shaaldy.booking.entity.Room;

@Mapper(componentModel = "spring")
public interface RoomMapper {

  /** Преобразовать Entity в Response */
  RoomResponse toResponse(Room room);

  /** Преобразовать CreateRequest в Entity (без hotel и bookings) */
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "hotel", ignore = true)
  @Mapping(target = "bookings", ignore = true)
  Room toEntity(CreateRoomRequest request);

  /** Обновить Entity значениями из UpdateRequest */
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "hotel", ignore = true)
  @Mapping(target = "bookings", ignore = true)
  void updateEntity(UpdateRoomRequest request, @MappingTarget Room room);
}
