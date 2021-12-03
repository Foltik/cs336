#!/bin/bash

mysql -u cs336 -p"cs336" -D cs336 <<EOF
drop database cs336;
create database cs336;
EOF
