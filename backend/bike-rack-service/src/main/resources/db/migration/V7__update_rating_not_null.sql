UPDATE bike_racks
SET rating = 0
WHERE rating IS NULL;

ALTER TABLE bike_racks
MODIFY rating DOUBLE NOT NULL;