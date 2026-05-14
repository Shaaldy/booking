package by.shaaldy.booking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import by.shaaldy.booking.dto.request.room.CreateRoomRequest;
import by.shaaldy.booking.dto.request.room.UpdateRoomRequest;
import by.shaaldy.booking.dto.response.RoomResponse;
import by.shaaldy.booking.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Rooms", description = "API для управления комнатами в отелях")
public class RoomController {

  private final RoomService roomService;

  /** POST /api/v1/rooms Создать новую комнату */
  @SneakyThrows
  @PostMapping
  @Operation(
      summary = "Создать новую комнату",
      description = "Создает новую комнату в отеле с информацией о типе, цене и вместимости")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Комната успешно создана"),
        @ApiResponse(
            responseCode = "400",
            description = "Ошибка валидации (неверная цена, вместимость или тип)"),
        @ApiResponse(responseCode = "404", description = "Отель не найден")
      })
  public ResponseEntity<RoomResponse> create(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные новой комнаты")
          @Valid
          @RequestBody
          CreateRoomRequest request) {
    log.info("POST /rooms - Create room");
    RoomResponse response = roomService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  /** GET /api/v1/rooms/{id} Получить комнату по ID */
  @SneakyThrows
  @GetMapping("/{id}")
  @Operation(
      summary = "Получить комнату по ID",
      description = "Возвращает информацию о комнате по её ID")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Комната найдена и возвращена"),
        @ApiResponse(responseCode = "404", description = "Комната не найдена")
      })
  public ResponseEntity<RoomResponse> getById(
      @Parameter(description = "ID комнаты", required = true, example = "1") @PathVariable
          Long id) {
    log.info("GET /rooms/{} - Get room by id", id);
    RoomResponse response = roomService.getById(id);
    return ResponseEntity.ok(response);
  }

  /** GET /api/v1/rooms/hotel/{hotelId} Получить все комнаты отеля */
  @SneakyThrows
  @GetMapping("/hotel/{hotelId}")
  @Operation(
      summary = "Получить все комнаты отеля",
      description = "Возвращает список всех комнат для указанного отеля")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Список комнат успешно получен"),
        @ApiResponse(responseCode = "404", description = "Отель не найден")
      })
  public ResponseEntity<List<RoomResponse>> getByHotelId(
      @Parameter(description = "ID отеля", required = true, example = "1") @PathVariable
          Long hotelId) {
    log.info("GET /rooms/hotel/{} - Get rooms by hotel", hotelId);
    List<RoomResponse> response = roomService.getByHotelId(hotelId);
    return ResponseEntity.ok(response);
  }

  /** PUT /api/v1/rooms/{id} Обновить комнату */
  @SneakyThrows
  @PutMapping("/{id}")
  @Operation(
      summary = "Обновить комнату",
      description = "Обновляет информацию о комнате (номер, тип, цена, вместимость)")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Комната успешно обновлена"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Комната не найдена")
      })
  public ResponseEntity<RoomResponse> update(
      @Parameter(description = "ID комнаты", required = true, example = "1") @PathVariable Long id,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Обновленные данные комнаты")
          @Valid
          @RequestBody
          UpdateRoomRequest request) {

    log.info("PUT /rooms/{} - Update room", id);
    RoomResponse response = roomService.update(id, request);
    return ResponseEntity.ok(response);
  }

  /** DELETE /api/v1/rooms/{id} Удалить комнату */
  @SneakyThrows
  @DeleteMapping("/{id}")
  @Operation(summary = "Удалить комнату", description = "Удаляет комнату из отеля")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Комната успешно удалена"),
        @ApiResponse(responseCode = "404", description = "Комната не найдена")
      })
  public ResponseEntity<Void> delete(
      @Parameter(description = "ID комнаты", required = true, example = "1") @PathVariable
          Long id) {
    log.info("DELETE /rooms/{} - Delete room", id);
    roomService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
