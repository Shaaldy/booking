package by.shaaldy.booking.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import by.shaaldy.booking.dto.request.hotel.CreateHotelRequest;
import by.shaaldy.booking.dto.request.hotel.UpdateHotelRequest;
import by.shaaldy.booking.dto.response.HotelResponse;
import by.shaaldy.booking.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/hotels")
@Slf4j
@Tag(name = "Hotels", description = "API для управления отелями")
public class HotelController {

  private final HotelService hotelService;

  @PostMapping
  public ResponseEntity<HotelResponse> create(@Valid @RequestBody CreateHotelRequest request) {
    HotelResponse response = hotelService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @SneakyThrows
  @GetMapping("/{id}")
  @Operation(
      summary = "Получить отель по ID",
      description = "Возвращает информацию об отеле по его ID")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Отель найден и возвращен"),
        @ApiResponse(responseCode = "404", description = "Отель не найден")
      })
  public ResponseEntity<HotelResponse> getById(
      @Parameter(description = "ID отеля", required = true, example = "1") @PathVariable Long id) {
    log.info("GET /hotels/{} - Get user by id", id);
    HotelResponse response = hotelService.getById(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping
  @Operation(
      summary = "Получить информацию всех отелей",
      description = "Возвращает список всех отелей с пагинацией")
  @ApiResponse(responseCode = "200", description = "Список отелей успешно получен")
  public ResponseEntity<Page<HotelResponse>> getAll(@ParameterObject Pageable pageable) {
    log.info("GET /users - Get all hotels, page: {}", pageable.getPageNumber());
    Page<HotelResponse> response = hotelService.getAll(pageable);
    return ResponseEntity.ok(response);
  }

  @SneakyThrows
  @PutMapping("/{id}")
  @Operation(summary = "Обновить информацию об отеле", description = "Обновляет информацию отеля")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Информация отеля успешно обновлена"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Отель не найден")
      })
  public ResponseEntity<HotelResponse> update(
      @Parameter(description = "ID отеля", required = true, example = "1") @PathVariable Long id,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Обновленные данные пользователя")
          @Valid
          @RequestBody
          UpdateHotelRequest request) {

    log.info("PUT /users/{} - Update user", id);
    HotelResponse response = hotelService.update(id, request);
    return ResponseEntity.ok(response);
  }

  @SneakyThrows
  @DeleteMapping("/{id}")
  @Operation(summary = "Удалить отель", description = "Удаляет отель и его данные")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Пользователь успешно удален"),
        @ApiResponse(responseCode = "404", description = "Пользователь не найден")
      })
  public ResponseEntity<Void> delete(
      @Parameter(description = "ID отеля", required = true, example = "1") @PathVariable Long id) {
    log.info("DELETE /hotels/{} - Delete user", id);
    hotelService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
