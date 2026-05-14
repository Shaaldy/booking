CREATE TABLE users(
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE hotels(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    address TEXT,
    rating NUMERIC(2, 1),
    created_at TIMESTAMP DEFAULT NOW()
);
CREATE INDEX idx_hotels_city
    ON hotels(city);

CREATE TABLE rooms(
    id BIGSERIAL PRIMARY KEY,
    hotel_id BIGINT NOT NULL REFERENCES hotels(id) ON DELETE CASCADE,
    number VARCHAR(50),
    type VARCHAR(50), -- single / double
    price_per_night NUMERIC(10, 2) NOT NULL,
    capacity INT NOT NULL
);
CREATE INDEX idx_rooms_hotel ON rooms(hotel_id);


CREATE TABLE bookings(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    room_id BIGINT NOT NULL REFERENCES rooms(id),
    check_in DATE NOT NULL,
    check_out DATE NOT NULL,
    status VARCHAR(20) NOT NULL, --  CREATED / CANCELLED
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP
);
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

CREATE INDEX idx_bookings_room_dates
    ON bookings(room_id, check_in, check_out);

