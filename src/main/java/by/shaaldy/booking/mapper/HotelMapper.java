package by.shaaldy.booking.mapper;


import by.shaaldy.booking.dto.CreateHotelRequest;
import by.shaaldy.booking.dto.HotelResponse;
import by.shaaldy.booking.dto.UpdateHotelRequest;
import by.shaaldy.booking.entity.Hotel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    HotelResponse toResponse(Hotel hotel);

    Hotel toEntity(CreateHotelRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateHotelRequest request, Hotel hotel);

}
