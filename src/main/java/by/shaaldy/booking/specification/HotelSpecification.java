package by.shaaldy.booking.specification;

import by.shaaldy.booking.entity.Hotel;
import org.springframework.data.jpa.domain.Specification;

public class HotelSpecification {

    public static Specification<Hotel> hasCity(String city){
        return (root, query, cb) ->
                city == null ? null : cb.equal(root.get("city"), city);
    }

    public static Specification<Hotel> hasRatingGreaterThan(Double rating){
        return (root, query, cb) ->
                rating == null ? null : cb.greaterThanOrEqualTo(root.get("rating"), rating);
    }
}
