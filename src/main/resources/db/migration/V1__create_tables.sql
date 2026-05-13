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

CREATE TABLE rooms(
    id BIGSERIAL PRIMARY KEY,
    hotel_id BIGINT NOT NULL REFERENCES hotels(id) ON DELETE CASCADE,
    number VARCHAR(50),
    type VARCHAR(50), -- single / double
    price_per_night NUMERIC(10, 2) NOT NULL,
    capacity INT NOT NULL
);

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
