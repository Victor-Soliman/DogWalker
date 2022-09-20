package main.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import main.controllers.dto.OrderRequest;
import main.controllers.dto.OrderResponse;
import main.services.OrderService;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "GET request documentation", description = "full documentation of this GET request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all good on POST"),
            @ApiResponse(responseCode = "400", description = "Bad boy GET request")
    }
    )
    @GetMapping
    public List<OrderResponse> findAll() {
        return orderService.findAll();
    }

    @Operation(summary = "GET request documentation", description = "full documentation of this GET request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all good on POST"),
            @ApiResponse(responseCode = "400", description = "Bad boy GET request")
    }
    )
    @GetMapping("orderDate")
    public List<OrderResponse> findByOrderDate(@RequestParam(name = "orderDate") Date date) {
        return orderService.findByOrderDate(date);
    }

    @Operation(summary = "GET request documentation", description = "full documentation of this GET request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all good on POST"),
            @ApiResponse(responseCode = "400", description = "Bad boy GET request")
    }
    )
    @GetMapping("returnDate")
    public List<OrderResponse> findByReturnDate(@RequestParam(name = "returnDate") Date date) {
        return orderService.findByReturnDate(date);
    }

    @PostMapping
    public OrderResponse save(@RequestBody OrderRequest orderRequest) {
        return orderService.save(orderRequest);
    }

    @PutMapping("{id}")
    public OrderResponse update(@PathVariable("id") Integer id, @RequestBody OrderRequest orderRequest) {
        return orderService.update(id, orderRequest);
    }

    @PatchMapping("/update/{id}/returnDate/{returnDate}")
    public OrderResponse updateReturnDate(@PathVariable(value = "id") Integer id,
                                          @PathVariable(value = "returnDate") Date date) {

        return orderService.updateReturnDate(id, date);
    }

    @PatchMapping("/update/{id}/orderDate/{orderDate}")
    public OrderResponse updateOrderDate(@PathVariable(value = "id") Integer id,
                                          @PathVariable(value = "orderDate") Date date) {

        return orderService.updateOrderDate(id, date);
    }

    @PatchMapping("/update/{id}/walkCost/{walkCost}")
    public OrderResponse updateWalkCost(@PathVariable(value = "id") Integer id,
                                          @PathVariable(value = "walkCost") Double cost) {

        return orderService.updateWalkCost(id, cost);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Integer id) {
        orderService.delete(id);
    }
}
