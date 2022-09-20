package main.mappers;

import main.controllers.dto.OrderResponse;
import main.repository.entities.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderResponseMapper {

    public OrderResponse map(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .returnDate(order.getReturnDate())
                .walk_cost(order.getWalk_cost())
                .dogWalked(order.getDogWalked())
//                .user(order.getUser())
//                .walker(order.getWalker())
//                .dog(order.getDog())
                .build();
    }
}
