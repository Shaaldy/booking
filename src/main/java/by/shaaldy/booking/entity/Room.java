package by.shaaldy.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "rooms", schema = "booking")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @Size(max = 50)
    @Column(name = "number", length = 50)
    private String number;

    @Size(max = 50)
    @Column(name = "type", length = 50)
    private String type;

    @NotNull
    @Column(name = "price_per_night", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerNight;

    @NotNull
    @Column(name = "capacity", nullable = false)
    private Integer capacity;


}