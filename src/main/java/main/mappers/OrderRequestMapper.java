package main.mappers;

import main.controllers.dto.OrderRequest;
import main.repository.entities.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class OrderRequestMapper {

    public Order map(OrderRequest orderRequest) {
        return Order.builder()
                .orderDate((Date) orderRequest.getOrderDate())
                .returnDate((Date) orderRequest.getReturnDate())
                .walk_cost(orderRequest.getWalk_cost())
                .dogWalked(orderRequest.getDogWalked())
//                .walker(orderRequest.getWalker())
//                .user(orderRequest.getUser())
//                .dog(orderRequest.getDog())
                .build();
    }
}
