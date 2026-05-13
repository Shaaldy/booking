package by.shaaldy.booking.mapper;

import org.mapstruct.*;

import by.shaaldy.booking.dto.request.room.CreateRoomRequest;
import by.shaaldy.booking.dto.request.room.UpdateRoomRequest;
import by.shaaldy.booking.dto.response.RoomResponse;
import by.shaaldy.booking.entity.Room;

@Mapper(componentModel = "spring")
public interface RoomMapper {
  RoomResponse toResponse(Room room);

  Room toEntity(CreateRoomRequest request);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "hotel", ignore = true)
  @Mapping(target = "bookings", ignore = true)
  void updateEntity(UpdateRoomRequest request, @MappingTarget Room room);
}
