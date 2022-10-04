package main.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.dto.OrderRequest;
import main.dto.OrderResponse;
import main.mappers.OrderRequestMapper;
import main.mappers.OrderResponseMapper;
import main.repository.OrdersRepository;
import main.repository.entities.Order;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

public class OrderService {

    private final OrdersRepository repository;
    private final OrderResponseMapper mapper;
    private final OrderRequestMapper requestMapper;


    public List<OrderResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer id) {
        Order order = repository.findById(id).orElseThrow(
                () -> new NotFoundException("* The Id : " + id + " was not found in the database"));

        return mapper.map(order);
    }

    public List<OrderResponse> findByOrderDate(Date date) {

        List<Order> ordersFromDataBase = repository.findOrdersByOrderDate(date);

        if (ordersFromDataBase.isEmpty()) {
            log.error("Order was not found in the database with order date : " + date);

            throw new NotFoundException("The Order date : " + date + " was not found in the database");
        }

        return ordersFromDataBase.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> findByReturnDate(Date date) {
        List<Order> ordersFromDataBase = repository.findOrdersByReturnDate(date);

        if (ordersFromDataBase.isEmpty()) {
            log.error("Order was not found in the database with return date : " + date);

            throw new NotFoundException("The Order date : " + date + " was not found in the database");
        }

        return ordersFromDataBase.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public OrderResponse save(OrderRequest orderRequest) {

        // we want to make the walkCost returned by the application

        Double costPerDay = orderRequest.getCostPerDay();
        Integer daysWalked = orderRequest.getDaysWalked();
        Double walkCost = daysWalked * costPerDay;

        Order orderToBeSaved = requestMapper.map(orderRequest);
        orderToBeSaved.setWalkCost(walkCost);

        repository.save(orderToBeSaved);

        log.info("Order was Successfully saved in the database ..");

        return mapper.map(orderToBeSaved);
    }

    public OrderResponse update(Integer id, OrderRequest orderRequest) {
        Optional<Order> orderFromDataBase = repository.findById(id);
        if (orderFromDataBase.isPresent()) {

            log.info("Order was found in the database");

            Order orderToBeUpdated = orderFromDataBase.get();

            setAllParameters(orderRequest, orderToBeUpdated);

            //we set the cost value because it is given by the application, not the user
            setWalkCostValue(orderRequest, orderToBeUpdated);

            Order saved = repository.save(orderToBeUpdated);

            log.info("Successful Update! .. Order was mapped to the database");

            return mapper.map(saved);
        } else {
            log.error("Order was not found in the database with id : " + id);

            throw new NotFoundException("* No Order found with id : " + id);
        }
    }


    public void delete(Integer id) {
        Optional<Order> orderFromDataBase = repository.findById(id);

        if (orderFromDataBase.isPresent()) {
            log.info("Order with id : " + id + " was found in the database..");

            repository.deleteById(id);

            log.info("Order with id : " + id + " was Successfully deleted..");

        } else {

            log.error("Order was not found in the database with id : " + id);

            throw new NotFoundException("* The Id : " + id + " was not found in the database");
        }
    }

    public OrderResponse updateOrderPartially(OrderRequest request) {
        Integer id = request.getId();

        Order orderFromDataBase = repository.findById(id).orElseThrow(
                () -> new NotFoundException("* Order was not found in the database with id : " + id));

        setParametersPartially(request, orderFromDataBase);

        repository.save(orderFromDataBase);

        // we set walkerCostValue because it is calculated by the application
        setWalkCostValue(request, orderFromDataBase);

        return mapper.map(orderFromDataBase);

    }

    // functionality
    public OrderResponse getTotalPrice(Integer id) {

        Order orderById = repository.findById(id).orElseThrow(
                () -> new NotFoundException("* The Id : " + id + " was not found in the database")
        );

        Double costPerDay = orderById.getCostPerDay();
        Integer daysWalked = orderById.getDaysWalked();
        Double walkCost = daysWalked * costPerDay;
        orderById.setWalkCost(walkCost);

        repository.save(orderById);

        return mapper.map(orderById);

    }

    // functionality
    public List<OrderResponse> findAllOrdersAfterDate(Date date) {
        List<Order> allOrdersAfterOrderDate = repository.findAllByOrderDateAfter(date);

        return allOrdersAfterOrderDate.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }


    // functionality
    public List<OrderResponse> findAllBeforeDate(Date date) {
        List<Order> allOrdersBeforeOrderDate = repository.findAllByOrderDateBefore(date);

        return allOrdersBeforeOrderDate.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }


    // functionality
    public List<OrderResponse> findAllWhoHasDaysWalkedGreaterThan(Integer number) {
        List<Order> topByDaysWalkedIsGreaterThan = repository.findByDaysWalkedIsGreaterThan(number);

        return topByDaysWalkedIsGreaterThan.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    // helper
    private void setAllParameters(OrderRequest orderRequest, Order orderToBeUpdated) {
        orderToBeUpdated.setOrderDate(orderRequest.getOrderDate());
        orderToBeUpdated.setReturnDate(orderRequest.getReturnDate());
        orderToBeUpdated.setCostPerDay(orderRequest.getCostPerDay());
        orderToBeUpdated.setDaysWalked(orderRequest.getDaysWalked());
        orderToBeUpdated.setDogWalked(orderRequest.getDogWalked());
        orderToBeUpdated.setWalker(orderRequest.getWalker());
        orderToBeUpdated.setUser(orderRequest.getUser());
        orderToBeUpdated.setDog(orderRequest.getDog());
    }

    // helper
    private void setParametersPartially(OrderRequest request, Order orderFromDataBase) {
        orderFromDataBase.setOrderDate(request.getOrderDate() == null ?
                orderFromDataBase.getOrderDate() : request.getOrderDate());

        orderFromDataBase.setReturnDate(request.getReturnDate() == null ?
                orderFromDataBase.getReturnDate() : request.getReturnDate());

        orderFromDataBase.setDaysWalked(request.getDaysWalked() == null ?
                orderFromDataBase.getDaysWalked() : request.getDaysWalked());

        orderFromDataBase.setCostPerDay(request.getCostPerDay() == null ?
                orderFromDataBase.getCostPerDay() : request.getCostPerDay());

        orderFromDataBase.setDogWalked(request.getDogWalked() == null ?
                orderFromDataBase.getDogWalked() : request.getDogWalked());

        orderFromDataBase.setWalker(request.getWalker() == null ?
                orderFromDataBase.getWalker() : request.getWalker());

        orderFromDataBase.setUser(request.getUser() == null ?
                orderFromDataBase.getUser() : request.getUser());

        orderFromDataBase.setDog(request.getDog() == null ?
                orderFromDataBase.getDog() : request.getDog());
    }

    //helper
    private void setWalkCostValue(OrderRequest orderRequest, Order orderFromDtaBase) {
        Double costPerDay = (orderRequest.getCostPerDay() == null ?
                orderFromDtaBase.getCostPerDay() : orderRequest.getCostPerDay());
        Integer daysWalked = (orderRequest.getDaysWalked() == null ?
                orderFromDtaBase.getDaysWalked() : orderRequest.getDaysWalked());

        Double walkCost = daysWalked * costPerDay;
        orderFromDtaBase.setWalkCost(walkCost);
    }
}

