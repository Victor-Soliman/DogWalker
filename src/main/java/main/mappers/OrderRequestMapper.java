package main.mappers;

import main.dto.OrderRequest;
import main.entities.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class OrderRequestMapper {

    public Order map(OrderRequest orderRequest) {
        return Order.builder()
                .orderDate((Date) orderRequest.getOrderDate())
                .returnDate((Date) orderRequest.getReturnDate())
                .daysWalked(orderRequest.getDaysWalked())
                .costPerDay(orderRequest.getCostPerDay())
                .dogWalked(orderRequest.getDogWalked())
                .walker(orderRequest.getWalker())
                .user(orderRequest.getUser())
                .dog(orderRequest.getDog())
                .build();
    }
}
