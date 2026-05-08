CREATE INDEX idx_rooms_hotel ON rooms(hotel_id);

CREATE INDEX idx_bookings_room_dates
ON bookings(room_id, check_in, check_out);

CREATE INDEX idx_hotels_city
ON hotels(city);