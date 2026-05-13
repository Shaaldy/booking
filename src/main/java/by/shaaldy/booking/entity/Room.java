package by.shaaldy.booking.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(
    name = "rooms",
    indexes = {@Index(name = "idx_rooms_hotel", columnList = "hotel_id")})
public class Room {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "hotel_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_rooms_hotel_id"))
  private Hotel hotel;

  @NotBlank
  @Column(length = 50)
  private String number;

  @NotBlank
  @Column(length = 50)
  private String type; // single, double, suite

  @NotNull
  @DecimalMin(value = "0.01")
  @Column(name = "price_per_night", precision = 10, scale = 2, nullable = false)
  private BigDecimal pricePerNight;

  @NotNull
  @Positive
  @Column(nullable = false)
  private Integer capacity;

  @OneToMany(
      mappedBy = "room",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  private List<Booking> bookings = new ArrayList<>();
}
