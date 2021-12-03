#!/bin/bash

mysql -u cs336 -p"cs336" -D cs336 <<EOF

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

insert into flight (aircraft_id, from_airport_id, to_airport_id, takeoff_time, landing_time, days, type, direction, fare) values (
    1,
    1, 2,
    '1970-01-01 16:00:00',
    '1970-01-01 21:00:00',
    'TUESDAY',
    'domestic',
    'one_way',
    100.00
);

insert into flight (aircraft_id, from_airport_id, to_airport_id, takeoff_time, landing_time, days, type, direction, fare) values (
    1,
    1, 2,
    '1970-01-01 15:00:00',
    '1970-01-01 20:30:00',
    'WEDNESDAY',
    'domestic',
    'one_way',
    75.00
);

insert into flight (aircraft_id, from_airport_id, to_airport_id, takeoff_time, landing_time, days, type, direction, fare) values (
    1,
    1, 2,
    '1970-01-01 14:00:00',
    '1970-01-01 18:30:00',
    'THURSDAY',
    'domestic',
    'one_way',
    80.00
);

EOF
