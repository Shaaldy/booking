package by.shaaldy.booking.specification;

import by.shaaldy.booking.entity.Hotel;
import org.springframework.data.jpa.domain.Specification;

public class HotelSpecifications {

    public static Specification<Hotel> hasCity(String city) {
        return (root, query, cb) ->
                city == null ? null : cb.equal(root.get("city"), city);
    }

    public static Specification<Hotel> hasRating(Double rating) {
        return (root, query, cb) ->
                rating == null ? null : cb.greaterThanOrEqualTo(root.get("rating"), rating);
    }

    public static Specification<Hotel> hasCapacity(Integer guests) {
        return (root, query, cb) -> {
            if (guests == null) return null;

            // join с rooms
            return cb.greaterThanOrEqualTo(
                    root.join("rooms").get("capacity"),
                    guests
            );
        };
    }
}