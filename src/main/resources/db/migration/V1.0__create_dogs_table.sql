DROP TABLE IF EXISTS dogs;

CREATE TABLE dogs
(
    id            int primary Key auto_increment,
    user_id       int,
#     walker_id     int,
    name          varchar(50),
    age           int,
    genealogy     varchar(50),
    has_microchip BOOLEAN,
    chip_number   varchar(50),
    FOREIGN KEY (user_id) REFERENCES users (id)
#     FOREIGN KEY (walker_id) REFERENCES walkers (id)

);

