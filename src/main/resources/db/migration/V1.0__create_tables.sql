

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
    user_role    varchar(25)
);


CREATE TABLE walkers
(
    id                  int primary Key auto_increment,
    name                varchar(50),
    age                 int,
    password            varchar(50),
    city                varchar(50),
    phone_number        varchar(50),
    address             varchar(100),
    email               varchar(100),
    years_of_experience int,
    is_available        BOOLEAN
);


CREATE TABLE dogs
(
    id            int primary Key auto_increment,
    name          varchar(50),
    age           int,
    genealogy     varchar(50),
    has_microchip BOOLEAN,
    chip_number   varchar(50)
);

CREATE TABLE orders
(
    id          int primary Key auto_increment,
    order_date  date,
    return_date date,
    walk_cost   double,
    dog_walked  BOOLEAN,
    days_walked   int,
    cost_per_day  double,
    walker_id   int NOT NULL,
    user_id     int NOT NULL,
    dog_id      int NOT NULL,
    FOREIGN KEY (walker_id) REFERENCES walkers (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (dog_id) REFERENCES dogs (id)
);

