package by.shaaldy.booking.repository;

import by.shaaldy.booking.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface HotelJpaRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel>  {
}
