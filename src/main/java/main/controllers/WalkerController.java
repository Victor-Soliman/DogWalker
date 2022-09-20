package main.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import main.controllers.dto.UserResponse;
import main.controllers.dto.WalkerRequest;
import main.controllers.dto.WalkerResponse;
import main.services.WalkerService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("walkers")
@Validated
public class WalkerController {

    private final WalkerService walkerService;

    @Operation(summary = "GET request documentation", description = "full documentation of this GET request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all good on POST"),
            @ApiResponse(responseCode = "400", description = "Bad boy GET request")
    }
    )
    @GetMapping()
    public List<WalkerResponse> findAll() {
        return walkerService.findAll();
    }

    @Operation(summary = "GET request documentation", description = "full documentation of this GET request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all good on POST"),
            @ApiResponse(responseCode = "400", description = "Bad boy GET request")
    }
    )
    @GetMapping("{id}")
    public WalkerResponse findById(@PathVariable Integer id) {
        return walkerService.findById(id);
    }

    @Operation(summary = "GET request documentation", description = "full documentation of this GET request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all good on GET"),
            @ApiResponse(responseCode = "400", description = "Bad boy GET request")
    }
    )
    @GetMapping("name")
    public List<WalkerResponse> findByName(@RequestParam(name = "name") String name) {
        return walkerService.findByName(name);
    }

    @Operation(summary = "POST request documentation", description = "full documentation of this POST request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all good on POST"),
            @ApiResponse(responseCode = "400", description = "Bad boy POST request")
    }
    )
    @PostMapping
    public WalkerResponse save(@Parameter(description = "Documented Model used as input for POST")
                                   @Valid @RequestBody WalkerRequest walkerRequest) {
        return walkerService.save(walkerRequest);
    }

    @Operation(summary = "PUT request documentation", description = "full documentation of this PUT request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all good on PUT"),
            @ApiResponse(responseCode = "400", description = "Bad boy PUT request")
    }
    )
    @PutMapping("{id}")
    public WalkerResponse updateTotal(@PathVariable("id") Integer id, @RequestBody WalkerRequest walkerRequest) {
        return walkerService.update(id, walkerRequest);
    }

    @Operation(summary = "Patch request documentation", description = "full documentation of this Patch request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all good on Patch"),
            @ApiResponse(responseCode = "400", description = "Bad boy Patch request")
    }
    )
    @PatchMapping("/update/{id}/password/{password}")
    public WalkerResponse updatePassword(@PathVariable(value = "id") Integer id,
                                         @PathVariable(value = "password") String password) {
        return walkerService.updatePassword(id, password);
    }

    @Operation(summary = "Patch request documentation", description = "full documentation of this Patch request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all good on Patch"),
            @ApiResponse(responseCode = "400", description = "Bad boy Patch request")
    }
    )
    @PatchMapping("/update/{id}/phone/{phone_number}")
    public WalkerResponse updatePhoneNumber(@PathVariable(value = "id") Integer id,
                                            @PathVariable(value = "phone_number") String phoneNumber) {
        return walkerService.updatePhoneNumber(id, phoneNumber);
    }


    @Operation(summary = "Patch request documentation", description = "full documentation of this Patch request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all good on Patch"),
            @ApiResponse(responseCode = "400", description = "Bad boy Patch request")
    }
    )
    @PatchMapping("/update/{id}/address/{address}")
    public WalkerResponse updateAddress(@PathVariable(value = "id") Integer id,
                                        @PathVariable(value = "address") String address) {
        return walkerService.updateAddress(id, address);
    }

    @Operation(summary = "Patch request documentation", description = "full documentation of this Patch request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all good on Patch"),
            @ApiResponse(responseCode = "400", description = "Bad boy Patch request")
    }
    )
    @PatchMapping("/update/{id}/email/{email}")
    public WalkerResponse updateEmail(@PathVariable(value = "id") Integer id,
                                      @PathVariable(value = "email") String email) {
        return walkerService.updateEmail(id, email);
    }

    @Operation(summary = "DELETE request documentation", description = "full documentation of this DELETE request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all good on DELETE"),
            @ApiResponse(responseCode = "400", description = "Bad boy DELETE request")
    }
    )
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Integer id) {
        walkerService.delete(id);
    }


}
