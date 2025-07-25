CREATE TABLE if NOT EXISTS beer
(
    id integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    beer_name           varchar(255),
    beer_style          varchar(255),
    upc                 varchar(255),
    quantity_on_hand    integer,
    price               decimal,
    created_date        timestamp,
    last_modified_date  timestamp
);

CREATE TABLE if NOT EXISTS customer
(
    id integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    customer_name       varchar(255),
    email               varchar(255),
    created_date        timestamp,
    last_modified_date  timestamp

);