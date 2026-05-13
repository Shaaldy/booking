package by.shaaldy.booking.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import by.shaaldy.booking.dto.request.user.CreateUserRequest;
import by.shaaldy.booking.dto.request.user.UpdateUserRequest;
import by.shaaldy.booking.dto.response.UserResponse;
import by.shaaldy.booking.service.UserService;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Users", description = "API для управления пользователями")
public class UserController {
  private final UserService userService;

  @PostMapping
  @Operation(
      summary = "Создать нового пользователя",
      description = "Создает нового пользователя с уникальным email")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Пользователь успешно создан"),
        @ApiResponse(
            responseCode = "400",
            description = "Ошибка валидации (неверный email, пароль < 6 символов)"),
        @ApiResponse(
            responseCode = "409",
            description = "Пользователь с таким email уже существует")
      })
  public ResponseEntity<UserResponse> create(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Данные нового пользователя)")
          @Valid
          @RequestBody
          CreateUserRequest request) {
    log.info("POST /users - Create user");
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request));
  }

  @SneakyThrows
  @GetMapping("/{id}")
  @Operation(
      summary = "Получить пользователя по ID",
      description = "Возвращает информацию о пользователе по его ID")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Пользователь найден и возвращен"),
        @ApiResponse(responseCode = "404", description = "Пользователь не найден")
      })
  public ResponseEntity<UserResponse> getById(
      @Parameter(description = "ID пользователя", required = true, example = "1") @PathVariable
          Long id) {
    log.info("GET /users/{} - Get user by id", id);
    UserResponse response = userService.getById(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping
  @Operation(
      summary = "Получить всех пользователей",
      description = "Возвращает список всех пользователей с пагинацией")
  @ApiResponse(responseCode = "200", description = "Список пользователей успешно получен")
  public ResponseEntity<Page<UserResponse>> getAll(@ParameterObject Pageable pageable) {
    log.info("GET /users - Get all users, page: {}", pageable.getPageNumber());
    Page<UserResponse> response = userService.getAll(pageable);
    return ResponseEntity.ok(response);
  }

  @SneakyThrows
  @GetMapping("/email/{email}")
  @Operation(
      summary = "Получить пользователя по email",
      description = "Возвращает информацию о пользователе по его email")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Пользователь найден"),
        @ApiResponse(responseCode = "404", description = "Пользователь с таким email не найден")
      })
  public ResponseEntity<UserResponse> getByEmail(
      @Parameter(description = "Email пользователя", required = true, example = "user@example.com")
          @PathVariable
          String email) {
    log.info("GET /users/email/{} - Get user by email", email);
    UserResponse response = userService.getByEmail(email);
    return ResponseEntity.ok(response);
  }

  @SneakyThrows
  @PutMapping("/{id}")
  @Operation(
      summary = "Обновить пользователя",
      description = "Обновляет информацию пользователя (email и/или пароль)")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Пользователь успешно обновлен"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
        @ApiResponse(
            responseCode = "409",
            description = "Email уже используется другим пользователем")
      })
  public ResponseEntity<UserResponse> update(
      @Parameter(description = "ID пользователя", required = true, example = "1") @PathVariable
          Long id,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Обновленные данные пользователя")
          @Valid
          @RequestBody
          UpdateUserRequest request) {

    log.info("PUT /users/{} - Update user", id);
    UserResponse response = userService.update(id, request);
    return ResponseEntity.ok(response);
  }

  @SneakyThrows
  @DeleteMapping("/{id}")
  @Operation(
      summary = "Удалить пользователя",
      description = "Удаляет пользователя и все его связанные данные")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Пользователь успешно удален"),
        @ApiResponse(responseCode = "404", description = "Пользователь не найден")
      })
  public ResponseEntity<Void> delete(
      @Parameter(description = "ID пользователя", required = true, example = "1") @PathVariable
          Long id) {
    log.info("DELETE /users/{} - Delete user", id);
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
