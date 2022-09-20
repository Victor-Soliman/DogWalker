package main.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import main.controllers.dto.UserRequest;
import main.controllers.dto.UserResponse;
import main.services.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @Operation(summary = "GET request documentation",
            description = "GET request for all users in the DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on GET"),
            @ApiResponse(responseCode = "400", description = "Bad GET request")
    }
    )

    @GetMapping
    public List<UserResponse> findAll() {
        return userService.findAll();
    }


    @Operation(summary = "GET request documentation",
            description = "GET request for a user with specific id passed on URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on GET"),
            @ApiResponse(responseCode = "400", description = "Bad GET request")
    }
    )
    @GetMapping({"/{id}"})
    public UserResponse findById(@PathVariable(name = "id") Integer id) {

        return userService.findById(id);
    }


    @Operation(summary = "GET request documentation",
            description = "GET request for a user with specific name passed on URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on GET"),
            @ApiResponse(responseCode = "400", description = "Bad boy GET request")
    }
    )
    @GetMapping("/name")
    public List<UserResponse> findByName(@RequestParam(name = "name") String name) {
        return userService.findByName(name);
    }


    @Operation(summary = "POST request documentation",
            description = "POST request for to save a user passed in JSON format")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on POST"),
            @ApiResponse(responseCode = "400", description = "Bad boy POST request")
    }
    )
    @PostMapping
    public UserResponse save(@Valid @RequestBody UserRequest userRequest) {

        return userService.save(userRequest);
    }

    @Operation(summary = "PUT request documentation",
            description = "PUT request for to update all parameters for " +
                    "user passed in JSON format with id passed in URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on PUT"),
            @ApiResponse(responseCode = "400", description = "Bad PUT request")
    }
    )
    @PutMapping("{id}")
    public UserResponse update(@PathVariable(value = "id") Integer id, @RequestBody UserRequest userRequest) {
        return userService.update(id, userRequest);
    }

    @Operation(summary = "PATCH request documentation",
            description = "PATCH request for to update a parameter(password) for " +
                    "user passed in JSON format with id passed in URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on PATCH"),
            @ApiResponse(responseCode = "400", description = "Bad PATCH request")
    }
    )

    @PatchMapping("/update/{id}/password")
    public UserResponse updatePassword(@PathVariable(value = "id") Integer id,
                                       @RequestParam(name = "password") String password) {
        UserResponse userById = userService.findById(id);
        userById.setPassword(password);
        return userService.updatePassword(id, password);

    }


    @Operation(summary = "PATCH request documentation",
            description = "PATCH request for to update a parameter(phone number) for " +
                    "user passed in JSON format with id passed in URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on PATCH"),
            @ApiResponse(responseCode = "400", description = "Bad PATCH request")
    }
    )
    @PatchMapping("/update/{id}/phone/{phone_number}")
    public UserResponse updatePhoneNumber(@PathVariable(value = "id") Integer id,
                                          @PathVariable(value = "phone_number") String phoneNumber) {
        return userService.updatePhoneNumber(id, phoneNumber);
    }


    @Operation(summary = "PATCH request documentation",
            description = "PATCH request for to update a parameter(address) for " +
                    "user passed in JSON format with id passed in URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on PATCH"),
            @ApiResponse(responseCode = "400", description = "Bad PATCH request")
    }
    )
    @PatchMapping("/update/{id}/address/{address}")
    public UserResponse updateAddress(@PathVariable(value = "id") Integer id,
                                      @PathVariable(value = "address") String address) {
        return userService.updateAddress(id, address);
    }


    @Operation(summary = "PATCH request documentation",
            description = "PATCH request for to update a parameter(email) for " +
                    "user passed in JSON format with id passed in URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on PATCH"),
            @ApiResponse(responseCode = "400", description = "Bad PATCH request")
    }
    )
    @PatchMapping("/update/{id}/email/{email}")
    public UserResponse updateEmail(@PathVariable(value = "id") Integer id,
                                    @PathVariable(value = "email") String email) {
        return userService.updateEmail(id, email);
    }


    @Operation(summary = "PATCH request documentation",
            description = "PATCH request for to update a parameter(user status) for " +
                    "user passed in JSON format with id passed in URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on PATCH"),
            @ApiResponse(responseCode = "400", description = "Bad PATCH request")
    }
    )
    @PatchMapping("/update/{id}/blocked/{user_blocked}")
    public UserResponse updateUserStatus(@PathVariable(value = "id") Integer id,
                                         @PathVariable(value = "user_blocked") Boolean blocked) {
        return userService.updateUserStatus(id, blocked.toString());
    }


    @Operation(summary = "DELETE request documentation",
            description = "DELETE request for to update a parameter(password) for " +
                    "user passed in JSON format with id passed in URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on DELETE"),
            @ApiResponse(responseCode = "400", description = "Bad DELETE request")
    }
    )
    @DeleteMapping("{id}")
    public void delete(@PathVariable(value = "id") Integer id) throws UnsupportedOperationException {
        userService.delete(id);
    }

}
