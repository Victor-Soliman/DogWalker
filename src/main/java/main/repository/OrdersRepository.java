package main.repository;

import main.repository.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Integer> {

    @Override
    Optional<Order> findById(Integer integer);

    List<Order> findOrdersByOrderDate(Date orderDate);

    List<Order> findOrdersByReturnDate(Date returnDate);

    List<Order> findAllByOrderDateAfter(Date orderDate);

    List<Order> findAllByOrderDateBefore(Date orderDate);

    List<Order> findByDaysWalkedIsGreaterThan(Integer number);


}
