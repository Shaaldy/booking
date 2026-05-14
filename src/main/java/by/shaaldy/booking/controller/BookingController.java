package by.shaaldy.booking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import by.shaaldy.booking.dto.request.booking.CreateBookingRequest;
import by.shaaldy.booking.dto.request.booking.UpdateBookingStatusRequest;
import by.shaaldy.booking.dto.response.BookingResponse;
import by.shaaldy.booking.exception.ResourceNotFoundException;
import by.shaaldy.booking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/** REST контроллер для управления бронированиями */
@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Bookings", description = "API для управления бронированиями отелей")
public class BookingController {

  private final BookingService bookingService;

  /** POST /api/v1/bookings Создать новое бронирование */
  @PostMapping
  @Operation(
      summary = "Создать новое бронирование",
      description =
          "Создает новое бронирование комнаты на указанные даты. "
              + "Система гарантирует отсутствие пересечений через БД constraints")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Бронирование успешно создано"),
        @ApiResponse(
            responseCode = "400",
            description = "Ошибка валидации (неверные даты, пользователь или комната)"),
        @ApiResponse(responseCode = "404", description = "Пользователь или комната не найдены"),
        @ApiResponse(responseCode = "409", description = "Комната уже забронирована на эти даты")
      })
  public ResponseEntity<BookingResponse> create(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Данные нового бронирования")
          @Valid
          @RequestBody
          CreateBookingRequest request) {
    log.info("POST /bookings - Create booking");
    BookingResponse response = null;
    try {
      response = bookingService.create(request);
    } catch (ResourceNotFoundException e) {
      throw new RuntimeException(e);
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  /** GET /api/v1/bookings/{id} Получить бронирование по ID */
  @SneakyThrows
  @GetMapping("/{id}")
  @Operation(
      summary = "Получить бронирование по ID",
      description = "Возвращает информацию о бронировании по его ID")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Бронирование найдено и возвращено"),
        @ApiResponse(responseCode = "404", description = "Бронирование не найдено")
      })
  public ResponseEntity<BookingResponse> getById(
      @Parameter(description = "ID бронирования", required = true, example = "1") @PathVariable
          Long id) {
    log.info("GET /bookings/{} - Get booking by id", id);
    BookingResponse response = bookingService.getById(id);
    return ResponseEntity.ok(response);
  }

  /** GET /api/v1/bookings/user/{userId} Получить активные бронирования пользователя */
  @SneakyThrows
  @GetMapping("/user/{userId}")
  @Operation(
      summary = "Получить активные бронирования пользователя",
      description =
          "Возвращает список активных бронирований (CREATED или CONFIRMED) для указанного пользователя")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Список бронирований успешно получен"),
        @ApiResponse(responseCode = "404", description = "Пользователь не найден")
      })
  public ResponseEntity<List<BookingResponse>> getByUserId(
      @Parameter(description = "ID пользователя", required = true, example = "1") @PathVariable
          Long userId) {
    log.info("GET /bookings/user/{} - Get bookings by user", userId);
    List<BookingResponse> response = bookingService.getByUserId(userId);
    return ResponseEntity.ok(response);
  }

  /** PATCH /api/v1/bookings/{id}/status Обновить статус бронирования */
  @SneakyThrows
  @PatchMapping("/{id}/status")
  @Operation(
      summary = "Обновить статус бронирования",
      description =
          "Обновляет статус бронирования с валидацией переходов.\n\n"
              + "Доступные переходы:\n"
              + "• CREATED → CONFIRMED или CANCELLED\n"
              + "• CONFIRMED → COMPLETED или CANCELLED\n"
              + "• CANCELLED → (финальное состояние, нельзя менять)\n"
              + "• COMPLETED → (финальное состояние, нельзя менять)")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Статус успешно обновлен"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации статуса"),
        @ApiResponse(responseCode = "404", description = "Бронирование не найдено"),
        @ApiResponse(
            responseCode = "409",
            description = "Недопустимый переход статуса (например, из CANCELLED)")
      })
  public ResponseEntity<BookingResponse> updateStatus(
      @Parameter(description = "ID бронирования", required = true, example = "1") @PathVariable
          Long id,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Новый статус бронирования")
          @Valid
          @RequestBody
          UpdateBookingStatusRequest request) {

    log.info("PATCH /bookings/{}/status - Update booking status", id);
    BookingResponse response = bookingService.updateStatus(id, request);
    return ResponseEntity.ok(response);
  }

  /** DELETE /api/v1/bookings/{id} Отменить бронирование */
  @SneakyThrows
  @DeleteMapping("/{id}")
  @Operation(
      summary = "Отменить бронирование",
      description =
          "Отменяет бронирование, установив его статус на CANCELLED. "
              + "Невозможно отменить уже отмененное или завершенное бронирование")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Бронирование успешно отменено"),
        @ApiResponse(responseCode = "404", description = "Бронирование не найдено"),
        @ApiResponse(
            responseCode = "409",
            description = "Невозможно отменить бронирование в текущем статусе")
      })
  public ResponseEntity<Void> cancel(
      @Parameter(description = "ID бронирования", required = true, example = "1") @PathVariable
          Long id) {
    log.info("DELETE /bookings/{} - Cancel booking", id);
    bookingService.cancel(id);
    return ResponseEntity.noContent().build();
  }
}
