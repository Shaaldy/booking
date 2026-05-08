

ALTER TABLE bookings
    ADD CONSTRAINT check_dates
        CHECK (check_in < check_out);

CREATE EXTENSION IF NOT EXISTS btree_gist;
ALTER TABLE bookings
    ADD CONSTRAINT no_overlapping_active_bookings
        EXCLUDE USING gist (
        room_id WITH =,
        daterange(check_in, check_out) WITH &&
        )
        WHERE (status = 'CREATED');