CREATE TABLE IF NOT EXISTS user (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(64) NOT NULL,
    password VARCHAR(64) NOT NULL,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
    role ENUM('admin', 'representative', 'customer'),
    PRIMARY KEY (id)
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
    days VARCHAR(64) NOT NULL,
    domain ENUM('domestic', 'international') NOT NULL,
    fare DECIMAL NOT NULL,
    FOREIGN KEY (aircraft_id) REFERENCES aircraft (id) ON DELETE CASCADE,
    FOREIGN KEY (to_airport_id) REFERENCES airport (id) ON DELETE CASCADE,
    FOREIGN KEY (from_airport_id) REFERENCES airport (id) ON DELETE CASCADE,
    PRIMARY KEY (id)
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
    qid INT NOT NULL,
    FOREIGN KEY (representative_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (qid) REFERENCES question (id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);



CREATE TABLE IF NOT EXISTS booking (
    id INT NOT NULL AUTO_INCREMENT,
    customer_id INT NOT NULL,
    type ENUM('first_class', 'business', 'economy') NOT NULL,
    fare DECIMAL NOT NULL,
    status ENUM('waiting', 'reserved') NOT NULL,
    created_on TIMESTAMP NOT NULL,
    purchased_on TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES user (id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS booking_flight (
    booking_id INT NOT NULL,
    flight_id INT NOT NULL,
    status ENUM('waiting', 'reserved') NOT NULL,
    updated TIMESTAMP NOT NULL,
    FOREIGN KEY (booking_id) REFERENCES booking (id) ON DELETE CASCADE,
    FOREIGN KEY (flight_id) REFERENCES flight (id) ON DELETE CASCADE,
    PRIMARY KEY (flight_id, booking_id)
);


insert into user (username, password, first_name, last_name, role) values ("cust1", "cust1", "Cust1first", "Cust1last", "customer");
insert into user (username, password, first_name, last_name, role) values ("cust2", "cust2", "Cust2first", "Cust2last", "customer");
insert into user (username, password, first_name, last_name, role) values ("rep", "rep", "Repfirst", "Replast", "representative");
insert into user (username, password, first_name, last_name, role) values ("admin", "admin", "Adminfirst", "Adminlast", "admin");

insert into airport (name) values ("New York");
insert into airport (name) values ("Denver");
insert into airport (name) values ("Orlando");
insert into airport (name) values ("Chicago");
insert into airport (name) values ("Salt Lake City");
insert into airport (name) values ("Austin");
insert into airport (name) values ("Los Angeles");
insert into airport (name) values ("Seattle");

insert into airline (name) values ("PlaneCo");

insert into aircraft (airline_id, model, seats) values (1, "Small Plane", 1);
insert into aircraft (airline_id, model, seats) values (1, "Medium Plane", 2);
insert into aircraft (airline_id, model, seats) values (1, "Big Plane", 3);

insert into flight (aircraft_id, from_airport_id, to_airport_id, takeoff_time, landing_time, days, domain, fare) values (
    1,
    1, 2,
    '1970-01-01 16:00:00',
    '1970-01-01 21:00:00',
    'TUESDAY',
    'domestic',
    100.00
);

insert into flight (aircraft_id, from_airport_id, to_airport_id, takeoff_time, landing_time, days, domain, fare) values (
    1,
    1, 2,
    '1970-01-01 15:00:00',
    '1970-01-01 20:30:00',
    'WEDNESDAY',
    'domestic',
    75.00
);

insert into flight (aircraft_id, from_airport_id, to_airport_id, takeoff_time, landing_time, days, domain, fare) values (
    1,
    1, 2,
    '1970-01-01 14:00:00',
    '1970-01-01 18:30:00',
    'THURSDAY',
    'domestic',
    80.00
);

insert into flight (aircraft_id, from_airport_id, to_airport_id, takeoff_time, landing_time, days, domain, fare) values (
    1,
    2, 1,
    '1970-01-01 14:00:00',
    '1970-01-01 18:30:00',
    'THURSDAY',
    'domestic',
    90.00
);

insert into flight (aircraft_id, from_airport_id, to_airport_id, takeoff_time, landing_time, days, domain, fare) values (
    1,
    1, 4,
    '1970-01-01 10:00:00',
    '1970-01-01 13:30:00',
    'THURSDAY',
    'domestic',
    45.00
);

insert into flight (aircraft_id, from_airport_id, to_airport_id, takeoff_time, landing_time, days, domain, fare) values (
    1,
    4, 2,
    '1970-01-01 14:30:00',
    '1970-01-01 17:30:00',
    'THURSDAY',
    'domestic',
    30.00
);


insert into flight (aircraft_id, from_airport_id, to_airport_id, takeoff_time, landing_time, days, domain, fare) values (
    1,
    4, 2,
    '1970-01-01 11:30:00',
    '1970-01-01 14:30:00',
    'THURSDAY',
    'domestic',
    35.00
);
