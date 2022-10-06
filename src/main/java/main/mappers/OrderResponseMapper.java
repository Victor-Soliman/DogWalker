package main.mappers;

import main.dto.OrderResponse;
import main.entities.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderResponseMapper {

    public OrderResponse map(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .returnDate(order.getReturnDate())
                .daysWalked(order.getDaysWalked())
                .costPerDay(order.getCostPerDay())
                .walkCost(order.getWalkCost())
                .dogWalked(order.getDogWalked())
                .user(order.getUser())
                .walker(order.getWalker())
                .dog(order.getDog())
                .build();
    }
}
