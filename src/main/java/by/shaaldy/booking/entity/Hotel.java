package by.shaaldy.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "hotels", schema = "booking")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 100)
    @NotNull
    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "address", length = Integer.MAX_VALUE)
    private String address;

    @Column(name = "rating", precision = 2, scale = 1)
    private BigDecimal rating;

    @ColumnDefault("now()")
    @Column(name = "created_at")
    private Instant createdAt;


}