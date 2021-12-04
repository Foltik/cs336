#!/bin/bash

mysql -u cs336 -p"cs336" -D cs336 <<EOF

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

EOF
