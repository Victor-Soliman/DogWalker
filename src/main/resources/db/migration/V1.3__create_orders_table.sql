# DROP TABLE IF EXISTS orders;
#
# CREATE TABLE orders
# (
#     id          int primary Key auto_increment,
#     walker_id   int,
#     user_id     int,
#     dog_id      int,
#     order_date  date,
#     return_date date,
#     walk_cost   double,
#     dog_walked  BOOLEAN,
#     FOREIGN KEY (walker_id) REFERENCES walkers (id),
#     FOREIGN KEY (user_id) REFERENCES users (id),
#     FOREIGN KEY (dog_id) REFERENCES dogs (id)
# );