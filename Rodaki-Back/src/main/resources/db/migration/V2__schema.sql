ALTER TABLE passenger_schedules 
CHANGE COLUMN schedule period ENUM('MORNING', 'AFTERNOON', 'NIGHT') NOT NULL;

ALTER TABLE passenger_schedules 
ADD COLUMN trip_type ENUM('ONE_WAY', 'RETURN', 'ROUND_TRIP') NOT NULL 
AFTER period;