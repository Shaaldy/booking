package by.shaaldy.booking.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(
    name = "hotels",
    indexes = {@Index(name = "idx_hotels_city", columnList = "city")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Hotel name cannot be blank")
  @Column(nullable = false)
  private String name;

  @NotBlank(message = "City cannot be blank")
  @Column(nullable = false, length = 100)
  private String city;

  @Column
  private String address;

  @DecimalMin(value = "0.0", inclusive = false, message = "Rating must be greater than 0")
  @DecimalMax(value = "5.0", message = "Rating cannot exceed 5")
  @Column(precision = 2, scale = 1)
  private Double rating;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @OneToMany(
      mappedBy = "hotel",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  @Builder.Default
  private List<Room> rooms = new ArrayList<>();

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }
}
