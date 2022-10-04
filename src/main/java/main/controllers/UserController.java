package main.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.dto.UserRequest;
import main.dto.UserResponse;
import main.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Operation(description = "GET request for all users in the DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on GET"),
            @ApiResponse(responseCode = "400", description = "Bad GET request")
    }
    )
    @GetMapping
    public List<UserResponse> findAll() {
        return userService.findAll();
    }


    @Operation(description = "GET request for a user with specific id passed on URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on GET"),
            @ApiResponse(responseCode = "400", description = "Bad GET request")
    }
    )
    @GetMapping({"/{id}"})
    public UserResponse findById(@PathVariable(name = "id") Integer id) {

        return userService.findById(id);
    }

    @Operation(description = "GET request for a user with specific name passed on URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on GET"),
            @ApiResponse(responseCode = "400", description = "Bad boy GET request")
    }
    )
    @GetMapping("/name")
    public List<UserResponse> findByName(@RequestParam(name = "name") String name) {
        return userService.findByName(name);
    }


    @Operation(description = "POST request for to save a user passed in JSON format")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on POST"),
            @ApiResponse(responseCode = "400", description = "Bad POST request")
    }
    )
    @PostMapping
    public UserResponse save(@Valid @RequestBody UserRequest userRequest) {
//        if(errors.hasErrors()){
//            log.info("Error processing userRequest: "+ userRequest);
//            throw new RuntimeException("Wrong user details");
//        }
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return userService.save(userRequest);
    }

    @Operation(description = "PUT request for to update all parameters for " +
                    "user passed in JSON format with id passed in URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on PUT"),
            @ApiResponse(responseCode = "400", description = "Bad PUT request")
    }
    )
    @PutMapping("{id}")
    public UserResponse update(@PathVariable(value = "id") Integer id,
                               @RequestBody UserRequest userRequest) {
        return userService.update(id, userRequest);
    }

    @Operation(description = "PATCH request for to update a parameter or few, for " +
                    "user passed in JSON format")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on PATCH"),
            @ApiResponse(responseCode = "400", description = "Bad PATCH request")
    }
    )

    @PatchMapping("/update")
    public UserResponse patch(@RequestBody UserResponse userResponse){

        return userService.updateUserPartially(userResponse);
    }


    @Operation(description = "DELETE request to delete a user in the database based on id passed on URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on DELETE"),
            @ApiResponse(responseCode = "400", description = "Bad DELETE request")
    }
    )
    @DeleteMapping("{id}")
    public void delete(@PathVariable(value = "id") Integer id) {
        userService.delete(id);
    }

    @Operation(description = "GET request to check blocked users ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on GET"),
            @ApiResponse(responseCode = "400", description = "Bad GET request")
    }
    )
    @GetMapping("/blocked")
    public List<UserResponse> findBlockedUsers(){

        return userService.findBlockedUsers();
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleExceptions(IllegalArgumentException exception){
        log.error(exception.getMessage(),exception);
        return exception.getMessage();
    }

}
