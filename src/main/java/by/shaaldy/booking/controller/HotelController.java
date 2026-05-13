package by.shaaldy.booking.controller;

import by.shaaldy.booking.dto.CreateHotelRequest;
import by.shaaldy.booking.dto.HotelResponse;
import by.shaaldy.booking.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelResponse> create(@Valid @RequestBody CreateHotelRequest request) {
        HotelResponse response = hotelService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}