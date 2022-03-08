CREATE TABLE if NOT EXISTS food
(
    id   integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name  varchar(255),
    type  varchar (255),
    segment varchar (25),
    protein_percentage decimal,
    created_date timestamp
);