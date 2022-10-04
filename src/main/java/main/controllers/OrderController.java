package main.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import main.dto.OrderRequest;
import main.dto.OrderResponse;
import main.services.OrderService;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(description = " GET request to find the orders on the database ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on GET"),
            @ApiResponse(responseCode = "400", description = "Bad GET request" )
    }
    )
    @GetMapping
    public List<OrderResponse> findAll() {
        return orderService.findAll();
    }

    @Operation(description = " GET request to find an order from the database based on his id, passed in the URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on GET"),
            @ApiResponse(responseCode = "400", description = "Bad GET request")
    }
    )
    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable(value = "id") Integer id) {

        return orderService.findById(id);
    }


    @Operation(description = "POST request to (Create) an order in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on POST"),
            @ApiResponse(responseCode = "400", description = "Bad POST request")
    }
    )
    @PostMapping
    public OrderResponse save(@RequestBody OrderRequest orderRequest) {
        return orderService.save(orderRequest);
    }

    @Operation(description = "PUT request to (Total Update) an order in the database based on id passed on URL ," +
            "and a request in body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on PUT"),
            @ApiResponse(responseCode = "400", description = "Bad PUT request")
    }
    )
    @PutMapping("{id}")
    public OrderResponse update(@PathVariable("id") Integer id, @RequestBody OrderRequest orderRequest) {
        return orderService.update(id, orderRequest);
    }


    @Operation(description = "PATCH request to (Partial update) an order in the database (Recommended more than 3 parameters) ," +
            "we send data in JSON to the server")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on PATCH"),
            @ApiResponse(responseCode = "400", description = "Bad PATCH request")
    }
    )
    @PatchMapping("/update")
    public OrderResponse patch(@RequestBody OrderRequest request){
      return orderService.updateOrderPartially(request);

    }

    @Operation(description = "DELETE request to delete an order in the database based on id passed on URL ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on GEt"),
            @ApiResponse(responseCode = "400", description = "Bad GET request")
    }
    )
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Integer id) {

        orderService.delete(id);
    }

    // raport
    @Operation(description = "Get request to find the total price needed to be payed based on the id and" +
            " the number of days walked for a dog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on GET"),
            @ApiResponse(responseCode = "400", description = "Bad GET request")
    }
    )
    @GetMapping("price/{id}")
    public OrderResponse getTotalPrice(@PathVariable(value = "id") Integer id){

       return orderService.getTotalPrice(id);
    }

    // raport
    @Operation(description = "Get request to find all orders after a specific date passed as a parameter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on GET"),
            @ApiResponse(responseCode = "400", description = "Bad GET request")
    }
    )
    @GetMapping("/after_date")
    public List<OrderResponse> findAllOrdersAfterDate(@RequestParam(name = "date") Date date){
        return orderService.findAllOrdersAfterDate(date);

    }

    // raport
    @Operation(description = "Get request to find all orders before a specific date passed as a parameter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on GET"),
            @ApiResponse(responseCode = "400", description = "Bad GET request")
    }
    )
    @GetMapping("/before_date")
    public List<OrderResponse> findAllOrdersBeforeDate(@RequestParam(name = "date") Date date){
        return orderService.findAllBeforeDate(date);

    }

    // raport
    @Operation(description = "Get request to find all orders where the days walked are more than a number passed as a parameter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on GET"),
            @ApiResponse(responseCode = "400", description = "Bad GET request")
    }
    )
    @GetMapping("/days_walked")
    public List<OrderResponse> findAllOrdersWhoHasDaysWalkedGreaterThan(@RequestParam(name = "number") Integer number){
        return orderService.findAllWhoHasDaysWalkedGreaterThan(number);

    }

    // raport
    @Operation(description = "GET request to find an order from the database based on his order date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on GET"),
            @ApiResponse(responseCode = "400", description = "Bad GET request")
    }
    )
    @GetMapping("orderDate")
    public List<OrderResponse> findByOrderDate(@RequestParam(name = "orderDate")  Date orderDate) {
        return orderService.findByOrderDate(orderDate);
    }

    // report
    @Operation(description = "GET request to find an order from the database based on his return date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on GEt"),
            @ApiResponse(responseCode = "400", description = "Bad GET request")
    }
    )
    @GetMapping("/returnDate")
    public List<OrderResponse> findByReturnDate(@RequestParam(name = "returnDate")Date date ){
        return orderService.findByReturnDate(date);
    }
}
