package main.services;

import lombok.RequiredArgsConstructor;
import main.controllers.dto.OrderRequest;
import main.controllers.dto.OrderResponse;
import main.mappers.OrderRequestMapper;
import main.mappers.OrderResponseMapper;
import main.repository.OrdersRepository;
import main.repository.entities.Order;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrdersRepository repository;
    private final OrderResponseMapper mapper;

    private final OrderRequestMapper requestMapper;

    public List<OrderResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> findByOrderDate(Date date) {
        return repository.findByOrderDate(Date.valueOf(date.toString())).stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> findByReturnDate(Date date) {
        return repository.findByReturnDate(Date.valueOf(date.toString())).stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public OrderResponse save(OrderRequest orderRequest) {
        Order orderToBeSaved = requestMapper.map(orderRequest);
        repository.save(orderToBeSaved);
        return mapper.map(orderToBeSaved);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public OrderResponse update(Integer id, OrderRequest orderRequest) {
        Optional<Order> orderFromDataBase = repository.findById(id);
        if (orderFromDataBase.isPresent()) {
            Order orderToBeUpdated = orderFromDataBase.get();
            orderToBeUpdated.setOrderDate(orderRequest.getOrderDate());
            orderToBeUpdated.setReturnDate(orderRequest.getReturnDate());
            orderToBeUpdated.setWalk_cost(orderRequest.getWalk_cost());
            orderToBeUpdated.setDogWalked(orderRequest.getDogWalked());

            Order saved = repository.save(orderToBeUpdated);

            return mapper.map(saved);
        } else {
            throw new UnsupportedOperationException("No Order found with id : " + id);
        }
    }

    public OrderResponse updateReturnDate(Integer id, Date date) {

        Order orderFromDataBase = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No Order found with id : " + id));

        orderFromDataBase.setReturnDate(date);

        Order saved = repository.save(orderFromDataBase);

        return mapper.map(saved);
    }

    public OrderResponse updateOrderDate(Integer id, Date date) {
        Order orderFromDataBase = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No Order found with id : " + id));

        orderFromDataBase.setOrderDate(date);

        Order saved = repository.save(orderFromDataBase);

        return mapper.map(saved);
    }

    public OrderResponse updateWalkCost(Integer id, Double cost) {
        Order orderFromDataBase = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No Order found with id : " + id));

        orderFromDataBase.setWalk_cost(cost);

        Order saved = repository.save(orderFromDataBase);

        return mapper.map(saved);
    }
}

