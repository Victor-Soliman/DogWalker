package main.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import main.controllers.dto.DogRequest;
import main.controllers.dto.DogResponse;
import main.controllers.dto.UserResponse;
import main.repository.entities.Dog;
import main.services.DogsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("dogs")
@Validated
public class DogController {

    private final DogsService dogsService;

    @Operation(description = "GET all the dogs on the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on GET"),
            @ApiResponse(responseCode = "400", description = "Bad GET request")
    }
    )
    @GetMapping
    public List<DogResponse> findAll() {
        return dogsService.findAll();
    }

    @Operation(description = "GET a dog from the database based on his id passed on URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on GET"),
            @ApiResponse(responseCode = "400", description = "Bad GET request")
    }
    )
    @GetMapping("{id}")
    public DogResponse findById(@PathVariable Integer id) {
        return dogsService.findById(id);
    }

    @Operation(description = "GET a dog from the database based on his id passed as a parameter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on GET"),
            @ApiResponse(responseCode = "400", description = "Bad GET request")
    }
    )
    @GetMapping("name")
    public List<DogResponse> findByName(@RequestParam String name) {
        return dogsService.findByName(name);
    }

    @Operation(description = "Post (Create) a dog in the database ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on Post"),
            @ApiResponse(responseCode = "400", description = "Bad Post request")
    }
    )
    @PostMapping
    public DogResponse save(@Valid @RequestBody DogRequest dogRequest) {

        return dogsService.save(dogRequest);
    }

    @Operation(description = "PUT (Total Update) a dog in the database based on id passed on URL ," +
                             " and a request body ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on PUT"),
            @ApiResponse(responseCode = "400", description = "Bad PUT request")
    }
    )
    @PutMapping("{id}")
    public DogResponse update(@PathVariable("id") Integer id, @RequestBody DogRequest dogRequest) {
        return dogsService.update(id, dogRequest);
    }

    @Operation(description = "PATCH (Partial update) a dog in the database (Recommended more than 3 parameters) ," +
                                " we send data in JSON to the server ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on PATCH"),
            @ApiResponse(responseCode = "400", description = "Bad PATCH request")
    }
    )
    @PatchMapping("/update")
    public DogResponse partialUpdate(@RequestBody DogRequest request) {
        return dogsService.updateDogPartially(request);
    }

    @Operation(description = "DELETE a dog in the database based on id passed on URL ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All good on DELETE"),
            @ApiResponse(responseCode = "400", description = "Bad DELETE request")
    }
    )
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Integer id) {
        dogsService.delete(id);
    }

}
