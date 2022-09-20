DROP TABLE IF EXISTS users;


CREATE TABLE users
(
    id           int primary Key auto_increment,
    name         varchar(50),
    age          int,
    password     varchar(50),
    city         varchar(50),
    phone_number varchar(50),
    address      varchar(100),
    email        varchar(100),
    has_dog      BOOLEAN,
    user_blocked BOOLEAN,
    user_role    varchar(25),
    dogs         varchar(50)

);