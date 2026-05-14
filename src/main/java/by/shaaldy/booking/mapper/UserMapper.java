package by.shaaldy.booking.mapper;

import org.mapstruct.*;

import by.shaaldy.booking.dto.request.user.CreateUserRequest;
import by.shaaldy.booking.dto.request.user.UpdateUserRequest;
import by.shaaldy.booking.dto.response.UserResponse;
import by.shaaldy.booking.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

  /** Entity → Response */
  UserResponse toResponse(User user);

  /** Request → Entity (создание) */
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "bookings", ignore = true)
  @Mapping(target = "passwordHash", ignore = true)
  User toEntity(CreateUserRequest request);

  /**
   * Request → Entity (обновление) ВОЗВРАЩАЕМЫЙ ТИП: void (обновляет существующий объект
   * через @MappingTarget)
   */
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "bookings", ignore = true)
  @Mapping(target = "passwordHash", ignore = true)
  void updateEntity(UpdateUserRequest request, @MappingTarget User user);
}
