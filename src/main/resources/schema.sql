CREATE TABLE IF NOT EXISTS user (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(64) NOT NULL,
    password VARCHAR(64) NOT NULL,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
    role ENUM('admin', 'representative', 'customer'),
    PRIMARY KEY (id)
);



CREATE TABLE IF NOT EXISTS booking (
    id INT NOT NULL AUTO_INCREMENT,
    customer_id INT NOT NULL,
    fare DECIMAL NOT NULL,
    created_on TIMESTAMP NOT NULL,
    purchased_on TIMESTAMP,
    type ENUM('first_class', 'business', 'economy') NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES user (id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS booking_flight (
    booking_id INT NOT NULL,
    flight_id INT NOT NULL,
    seat INT NOT NULL,
    status ENUM('waiting', 'reserved') NOT NULL,
    FOREIGN KEY (booking_id) REFERENCES booking (id) ON DELETE CASCADE,
    FOREIGN KEY (flight_id) REFERENCES booking (id) ON DELETE CASCADE,
    PRIMARY KEY (flight_id, booking_id)
);



CREATE TABLE IF NOT EXISTS airport (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS airline (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS airline_airport (
    airline_id INT NOT NULL,
    airport_id INT NOT NULL,
    FOREIGN KEY (airline_id) REFERENCES airline (id) ON DELETE CASCADE,
    FOREIGN KEY (airport_id) REFERENCES airport (id) ON DELETE CASCADE,
    PRIMARY KEY (airline_id, airport_id)
);

CREATE TABLE IF NOT EXISTS aircraft (
    id INT NOT NULL AUTO_INCREMENT,
    airline_id INT NOT NULL,
    model VARCHAR(64) NOT NULL,
    seats INT NOT NULL,
    FOREIGN KEY (airline_id) REFERENCES airline (id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS flight (
    id INT NOT NULL AUTO_INCREMENT,
    aircraft_id INT NOT NULL,
    from_airport_id INT NOT NULL,
    to_airport_id INT NOT NULL,
    takeoff_time DATETIME NOT NULL,
    landing_time DATETIME NOT NULL,
    type ENUM('domestic', 'international') NOT NULL,
    direction ENUM('one_way', 'round_trip') NOT NULL,
    FOREIGN KEY (aircraft_id) REFERENCES aircraft (id) ON DELETE CASCADE,
    FOREIGN KEY (to_airport_id) REFERENCES airport (id) ON DELETE CASCADE,
    FOREIGN KEY (from_airport_id) REFERENCES airport (id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS flight_day (
    flight_id INT NOT NULL,
    day ENUM('monday', 'tuesday', 'wednesday', 'thursday', 'friday') NOT NULL,
    FOREIGN KEY (flight_id) REFERENCES flight (id),
    PRIMARY KEY (flight_id, day)
);



CREATE TABLE IF NOT EXISTS question (
    id INT NOT NULL AUTO_INCREMENT,
    customer_id INT NOT NULL,
    title VARCHAR(255),
    body TEXT,
    FOREIGN KEY (customer_id) REFERENCES user (id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS answer (
    id INT NOT NULL AUTO_INCREMENT,
    representative_id INT NOT NULL,
    body TEXT,
    FOREIGN KEY (representative_id) REFERENCES user (id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS question_answer (
    question_id INT NOT NULL,
    answer_id INT NOT NULL,
    FOREIGN KEY (question_id) REFERENCES question (id) ON DELETE CASCADE,
    FOREIGN KEY (answer_id) REFERENCES answer (id) ON DELETE CASCADE,
    PRIMARY KEY (question_id, answer_id)
);