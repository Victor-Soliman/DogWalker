package main.services;

import main.dto.OrderRequest;
import main.dto.OrderResponse;
import main.entities.*;
import main.mappers.OrderRequestMapper;
import main.mappers.OrderResponseMapper;
import main.repository.OrdersRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.webjars.NotFoundException;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private OrderResponseMapper orderResponseMapper;

    @Mock
    private OrderRequestMapper orderRequestMapper;

    @InjectMocks
    private OrderService orderService;

    private static final Integer ID = 1;
    private static final Integer ID2 = 2;
    private static final Walker WALKER_RECORD = new Walker(ID, "James Martin", 19, "jie28@",
            "Bucharest", "0784751236", "Camil Resu 78", "martin11@gmail.com",
            1, true);

    private static final User USER_RECORD = new User(ID, "Alina Lacatus", 25, "Alinfata1",
            "Bucharest", "0726969875", "Mircea voda 23", "alina3@gmail.com", true,
            false, UserRole.ROLE_USER);

    private static final Dog DOG_RECORD = new Dog(ID, "Sun", 6, "Labrador",
            true, "89-784-6532");

    private static final Order FIRST_ORDER = new Order(ID, Date.valueOf("2022-09-06"), Date.valueOf("2022-09-17"),
            10, 80.0, 800.0, true, WALKER_RECORD, USER_RECORD, DOG_RECORD);

    private static final Order SECOND_ORDER = new Order(ID, Date.valueOf("2022-09-06"), Date.valueOf("2022-09-17"),
            10, 80.0, 800.0, true, WALKER_RECORD, USER_RECORD, DOG_RECORD);

    private static final List<Order> ORDERS = List.of(FIRST_ORDER, SECOND_ORDER);

    private final OrderResponse orderResponse = new OrderResponse(ID, Date.valueOf("2022-09-06"),
            Date.valueOf("2022-09-17"), 10, 80.0, 800.0, true, WALKER_RECORD, USER_RECORD, DOG_RECORD);

    private final OrderRequest orderRequest = new OrderRequest(ID, Date.valueOf("2022-09-06"),
            Date.valueOf("2022-09-17"), 10, 80.0, true, WALKER_RECORD,
            USER_RECORD, DOG_RECORD);

    Double costPerDay = (orderRequest.getCostPerDay() == null ?
            FIRST_ORDER.getCostPerDay() : orderRequest.getCostPerDay());
    Integer daysWalked = (orderRequest.getDaysWalked() == null ?
            FIRST_ORDER.getDaysWalked() : orderRequest.getDaysWalked());


    @Test
    void findAll() {
        Mockito.when(ordersRepository.findAll()).thenReturn(ORDERS);
        List<OrderResponse> expected = ordersRepository.findAll().stream()
                .map(orderResponseMapper::map).toList();

        Assertions.assertEquals(expected, orderService.findAll());
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void findById(Integer id) {

        Mockito.when(ordersRepository.findById(id)).thenReturn(Optional.of(FIRST_ORDER));
        Order order = Optional.of(FIRST_ORDER).get();
        OrderResponse expected = orderResponseMapper.map(order);

        Assertions.assertEquals(expected, orderService.findById(id));

    }

    @ParameterizedTest
    @ValueSource(ints = {2})
    void findByIdException(Integer id) {
        Mockito.when(ordersRepository.findById(id)).thenThrow(NotFoundException.class);
        Assertions.assertThrows(NotFoundException.class, () -> orderService.findById(id));

    }

    @ParameterizedTest
    @ValueSource(strings = {"2022-09-06"})
    void findByOrderDate(Date date) {
        Mockito.when(ordersRepository.findOrdersByOrderDate(Date.valueOf("2022-09-06"))).thenReturn(ORDERS);

        List<OrderResponse> expected = ORDERS.stream()
                .map(orderResponseMapper::map)
                .collect(Collectors.toList());

        Assertions.assertEquals(expected, orderService.findByOrderDate(date));

    }

    @ParameterizedTest
    @ValueSource(strings = {"2022-09-06"})
    void findByOrderDateException(Date date) {
        Mockito.when(ordersRepository.findOrdersByOrderDate(Date.valueOf("2022-09-06")))
                .thenThrow(NotFoundException.class);

        Assertions.assertThrows(NotFoundException.class, () -> orderService.findByOrderDate(date));
    }

    @ParameterizedTest
    @ValueSource(strings = "2022-09-17")
    void findByReturnDate(Date date) {
        Mockito.when(ordersRepository.findOrdersByReturnDate(Date.valueOf("2022-09-17")))
                .thenReturn(ORDERS);

        List<OrderResponse> expected = ORDERS.stream().map(order -> orderResponseMapper.map(order)).toList();

        Assertions.assertEquals(expected, orderService.findByReturnDate(date));
    }

    @ParameterizedTest
    @ValueSource(strings = "2022-09-17")
    void findByReturnDateException(Date date) {
        Mockito.when(ordersRepository.findOrdersByReturnDate(Date.valueOf("2022-09-17")))
                .thenThrow(NotFoundException.class);

        Assertions.assertThrows(NotFoundException.class, () -> orderService.findByReturnDate(date));

    }

    @Test
    void save() {
        Mockito.when(orderRequestMapper.map(orderRequest)).thenReturn(FIRST_ORDER);
        Mockito.when(ordersRepository.save(FIRST_ORDER)).thenReturn(FIRST_ORDER);

        OrderResponse expected = orderResponseMapper.map(FIRST_ORDER);

        Assertions.assertEquals(expected, orderService.save(orderRequest));
    }

    @Test
    void setTotalParametersTest() {
        Assertions.assertEquals(orderRequest.getOrderDate(), FIRST_ORDER.getOrderDate());
        Assertions.assertEquals(orderRequest.getReturnDate(), FIRST_ORDER.getReturnDate());
        Assertions.assertEquals(orderRequest.getCostPerDay(), FIRST_ORDER.getCostPerDay());
        Assertions.assertEquals(orderRequest.getDaysWalked(), FIRST_ORDER.getDaysWalked());
        Assertions.assertEquals(orderRequest.getDogWalked(), FIRST_ORDER.getDogWalked());
        Assertions.assertEquals(orderRequest.getWalker(), FIRST_ORDER.getWalker());
        Assertions.assertEquals(orderRequest.getUser(), FIRST_ORDER.getUser());
        Assertions.assertEquals(orderRequest.getDog(), FIRST_ORDER.getDog());

    }

    @Test
    void setTotalWalkCostTest() {
        Assertions.assertEquals(FIRST_ORDER.getWalkCost(), daysWalked * costPerDay);
    }

    @Test
    void update() {
        Mockito.when(ordersRepository.findById(ID)).thenReturn(Optional.of(FIRST_ORDER));

        Order order = Optional.of(FIRST_ORDER).get();
        setTotalParametersTest();

        setTotalWalkCostTest();

        Mockito.when(ordersRepository.save(order)).thenReturn(FIRST_ORDER);

        OrderResponse expected = orderResponseMapper.map(order);

        Assertions.assertEquals(expected, orderService.update(ID, orderRequest));

    }

    @Test
    void updateException() {
        Mockito.when(ordersRepository.findById(ID)).thenThrow(NotFoundException.class);

        Assertions.assertThrows(NotFoundException.class, () -> orderService.update(ID, orderRequest));
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void delete(Integer id) {
        Mockito.when(ordersRepository.findById(id)).thenReturn(Optional.of(FIRST_ORDER));
        Integer orderId = FIRST_ORDER.getId();

        Assertions.assertEquals(FIRST_ORDER, ordersRepository.findById(orderId).get());
        Mockito.verifyNoMoreInteractions(ordersRepository);
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void deleteException(Integer ID) {
        Mockito.when(ordersRepository.findById(ID)).thenThrow(NotFoundException.class);

        Assertions.assertThrows(NotFoundException.class, () -> orderService.delete(ID));

        Mockito.verifyNoMoreInteractions(ordersRepository);
    }

    @Test
    void setParametersPartiallyTest() {

        Assertions.assertEquals(SECOND_ORDER.getOrderDate(), orderRequest.getOrderDate() == null ?
                SECOND_ORDER.getOrderDate() : orderRequest.getOrderDate());

        Assertions.assertEquals(SECOND_ORDER.getReturnDate(), orderRequest.getReturnDate() == null ?
                SECOND_ORDER.getReturnDate() : orderRequest.getReturnDate());

        Assertions.assertEquals(SECOND_ORDER.getDaysWalked(), orderRequest.getDaysWalked() == null ?
                SECOND_ORDER.getDaysWalked() : orderRequest.getDaysWalked());

        Assertions.assertEquals(SECOND_ORDER.getCostPerDay(), orderRequest.getCostPerDay() == null ?
                SECOND_ORDER.getCostPerDay() : orderRequest.getCostPerDay());

        Assertions.assertEquals(SECOND_ORDER.getDogWalked(), orderRequest.getDogWalked() == null ?
                SECOND_ORDER.getDogWalked() : orderRequest.getDogWalked());

        Assertions.assertEquals(SECOND_ORDER.getWalker(), orderRequest.getWalker() == null ?
                SECOND_ORDER.getWalker() : orderRequest.getWalker());

        Assertions.assertEquals(SECOND_ORDER.getUser(), orderRequest.getUser() == null ?
                SECOND_ORDER.getUser() : orderRequest.getUser());

        Assertions.assertEquals(SECOND_ORDER.getDog(), orderRequest.getDog() == null ?
                SECOND_ORDER.getDog() : orderRequest.getDog());
    }

    @Test
    void updateOrderPartially() {
        Mockito.when(ordersRepository.findById(ID)).thenReturn(Optional.of(FIRST_ORDER));

        setParametersPartiallyTest();

        Mockito.when(ordersRepository.save(FIRST_ORDER)).thenReturn(FIRST_ORDER);

        setTotalWalkCostTest();

        OrderResponse expected = orderResponseMapper.map(FIRST_ORDER);

        Assertions.assertEquals(expected, orderService.updateOrderPartially(orderRequest));

    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void getTotalPrice(Integer id) {
        Mockito.when(ordersRepository.findById(id)).thenReturn(Optional.of(FIRST_ORDER));
        Order order = Optional.of(FIRST_ORDER).get();

        Double costPerDay = order.getCostPerDay();
        Integer daysWalked = order.getDaysWalked();
        Double walkCost = daysWalked * costPerDay;
        order.setWalkCost(walkCost);

        Mockito.when(ordersRepository.save(order)).thenReturn(order);

        OrderResponse expected = orderResponseMapper.map(order);

        Assertions.assertEquals(expected, orderService.getTotalPrice(id));

    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void getTotalPriceException(Integer id) {
        Mockito.when(ordersRepository.findById(id)).thenThrow(NotFoundException.class);
        Assertions.assertThrows(NotFoundException.class, () -> orderService.getTotalPrice(id));

    }

    @ParameterizedTest
    @ValueSource(strings = {"2022-09-6"})
    void findAllOrdersAfterDate(Date date) {
        Mockito.when(ordersRepository.findAllByOrderDateAfter(Date.valueOf("2022-09-6"))).thenReturn(ORDERS);

        List<OrderResponse> expected = ORDERS.stream().map(order -> orderResponseMapper.map(order)).toList();

        Assertions.assertEquals(expected, orderService.findAllOrdersAfterDate(date));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2022-09-17"})
    void findAllBeforeDate(Date date) {
        Mockito.when(ordersRepository.findAllByOrderDateBefore(Date.valueOf("2022-09-17"))).thenReturn(ORDERS);

        List<OrderResponse> expected = ORDERS.stream().map(order -> orderResponseMapper.map(order)).toList();

        Assertions.assertEquals(expected,orderService.findAllBeforeDate(date));
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void findAllWhoHasDaysWalkedGreaterThan(Integer number) {
        Mockito.when(ordersRepository.findByDaysWalkedIsGreaterThan(number)).thenReturn(ORDERS);

        List<OrderResponse> expected = ORDERS.stream().map(order -> orderResponseMapper.map(order)).toList();

        Assertions.assertEquals(expected,orderService.findAllWhoHasDaysWalkedGreaterThan(number));

    }

}