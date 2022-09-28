package main.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
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

    @Operation(description = "GET request to find  all walkers ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on GET"),
            @ApiResponse(responseCode = "400", description = "Bad GET request")
    }
    )
    @GetMapping()
    public List<WalkerResponse> findAll() {
        return walkerService.findAll();
    }

    @Operation(description = "GET request to find a walker based on his id passed in the URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on GET"),
            @ApiResponse(responseCode = "400", description = "Bad GET request")
    }
    )
    @GetMapping("{id}")
    public WalkerResponse findById(@PathVariable Integer id) {
        return walkerService.findById(id);
    }

    @Operation(description = "GET request to find walkers based on there name" +
                    " passed in the URL as a request parameter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all good on GET"),
            @ApiResponse(responseCode = "400", description = "Bad boy GET request")
    }
    )
    @GetMapping("name")
    public List<WalkerResponse> findByName(@RequestParam(name = "name") String name) {
        return walkerService.findByName(name);
    }

    @Operation(description = "Post request to (Create) a walker in the database")
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

    @Operation(description = "PUT request to (Total Update) a walker in the database based on id passed on URL ," +
            " and a request in body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all good on PUT"),
            @ApiResponse(responseCode = "400", description = "Bad boy PUT request")
    }
    )
    @PutMapping("{id}")
    public WalkerResponse updateTotal(@PathVariable("id") Integer id, @RequestBody WalkerRequest walkerRequest) {
        return walkerService.update(id, walkerRequest);
    }

    @Operation(description = "PATCH request to(Partial update) a dog in the database (Recommended more than 3 parameters) ," +
          "we send data in JSON to the server")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all good on Patch"),
            @ApiResponse(responseCode = "400", description = "Bad boy Patch request")
    }
    )
    @PatchMapping("/update")
    public WalkerResponse updateWalkerPartially(@RequestBody WalkerRequest request){
        return walkerService.updateWalkerPartially(request);
    }

    @Operation(description = "DELETE request to delete a dog in the database based on id passed on URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all good on DELETE"),
            @ApiResponse(responseCode = "400", description = "Bad boy DELETE request")
    }
    )
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Integer id) {
        walkerService.delete(id);
    }

    @Operation(description = "GET request to find available walkers ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on GET"),
            @ApiResponse(responseCode = "400", description = "Bad GET request")
    }
    )
    @GetMapping("/available")
    public List<WalkerResponse> findAvailableWalkers() {

        return walkerService.checkWalkerAvailability();

    }

    @Operation(description = "GET request to find walkers based on there years of experience passed in the URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on GET"),
            @ApiResponse(responseCode = "400", description = "Bad GET request")
    }
    )
    @GetMapping("/years_of_experience")
    public List<WalkerResponse> findTopWalkersByYearsOfExperience(@RequestParam(name = "years") Integer years){
       return walkerService.findTopWalkersByYearsOfExperienceGreaterThan(years);
    }
}
