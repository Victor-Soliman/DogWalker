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

    @Operation(summary = "GET request documentation", description = "full documentation of this GET request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all good on POST"),
            @ApiResponse(responseCode = "400", description = "Bad boy GET request")
    }
    )
    @GetMapping
    public List<DogResponse> findAll() {
        return dogsService.findAll();
    }

    @Operation(summary = "GET request documentation", description = "full documentation of this GET request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all good on POST"),
            @ApiResponse(responseCode = "400", description = "Bad boy GET request")
    }
    )
    @GetMapping("{id}")
    public DogResponse findById(@PathVariable Integer id) {
        return dogsService.findById(id);
    }

    @Operation(summary = "GET request documentation", description = "full documentation of this GET request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all good on POST"),
            @ApiResponse(responseCode = "400", description = "Bad boy GET request")
    }
    )
    @GetMapping("name")
    public List<DogResponse> findByName(@RequestParam String name) {
        return dogsService.findByName(name);
    }

    @PostMapping
    public DogResponse save(@Valid @RequestBody DogRequest dogRequest) {

        return dogsService.save(dogRequest);
    }

    @PutMapping("{id}")
    public DogResponse update(@PathVariable("id") Integer id, @RequestBody DogRequest dogRequest) {
        return dogsService.update(id, dogRequest);
    }

    @PatchMapping("/update/{id}/hasMicrochip/{microchip}")
    public DogResponse updateMicrochipStatus(@PathVariable(value = "id") Integer id,
                                             @PathVariable(value = "microchip") Boolean microchip) {
        return dogsService.updateMicrochipStatus(id, microchip.toString());
    }

    @PatchMapping("/update/{id}/age/{age}")
    public DogResponse updateAge(@PathVariable(value = "id") Integer id,
                                             @PathVariable(value = "age") Integer age) {
        return dogsService.updateAge(id, age);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Integer id) {
        dogsService.delete(id);
    }

}
