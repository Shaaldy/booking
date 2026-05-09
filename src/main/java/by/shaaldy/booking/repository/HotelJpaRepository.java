package by.shaaldy.booking.repository;

import by.shaaldy.booking.entity.Hotel;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.lang.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface HotelJpaRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {

    @Override
    @EntityGraph(attributePaths = "rooms")
    Optional<Hotel> findById(Long id);
}