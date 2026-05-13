package by.shaaldy.booking.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import by.shaaldy.booking.dto.request.user.CreateUserRequest;
import by.shaaldy.booking.dto.request.user.UpdateUserRequest;
import by.shaaldy.booking.dto.response.UserResponse;
import by.shaaldy.booking.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "passwordHash", ignore = true)
  UserResponse toResponse(User user);

  @Mapping(target = "bookings", ignore = true)
  User toEntity(CreateUserRequest request);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "bookings", ignore = true)
  void updateEntity(UpdateUserRequest request, User user);
}
