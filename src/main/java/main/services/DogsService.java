package main.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.controllers.dto.DogRequest;
import main.controllers.dto.DogResponse;
import main.controllers.dto.UserResponse;
import main.mappers.DogRequestMapper;
import main.mappers.DogResponseMapper;
import main.repository.DogsRepository;
import main.repository.entities.Dog;
//import main.repository.entities.Walker;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

public class DogsService {

    private final DogsRepository dogsRepository;
    private final DogResponseMapper mapper;
    private final DogRequestMapper requestMapper;

    public List<DogResponse> findAll() {
        return dogsRepository.findAll()
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public DogResponse findById(Integer id) {

        Dog dogFromDataBase = dogsRepository.findById(id).orElseThrow(
                () -> new NotFoundException("* Dog with id: " + id + " is not present in the DataBase")
        );

        return mapper.map(dogFromDataBase);
    }


    public List<DogResponse> findByName(String name) {
        List<Dog> dogsFromDataBase = dogsRepository.findByName(name);

        if (dogsFromDataBase.isEmpty()) {
            throw new NotFoundException("* Dog with name : " + name + " is not present in the DataBase");
        } else {

            return dogsFromDataBase.stream()
                    .map(mapper::map)
                    .collect(Collectors.toList());
        }
    }

    public DogResponse save(DogRequest dogRequest) {
        Dog mappedDog = requestMapper.map(dogRequest);
        dogsRepository.save(mappedDog);

        log.info("Dog was Successfully saved in the database ..");

        return mapper.map(mappedDog);
    }

    public void delete(Integer id) {
        Optional<Dog> dogFromDataBase = dogsRepository.findById(id);

        if (dogFromDataBase.isPresent()) {

            log.info("Dog with id : " + id + " was found in the database..");

            dogsRepository.deleteById(id);

            log.info("Dog with id : " + id + " was Successfully deleted..");


        } else {
            throw new NotFoundException("* Dog with id : " + id + " is not present in the DataBase");

        }
    }

    public DogResponse update(Integer id, DogRequest dogRequest) {

        Optional<Dog> dogFromDataBase = dogsRepository.findById(id);

        if (dogFromDataBase.isPresent()) {

            log.info("Dog was found in the database");

            Dog dogToBeUpdated = setAllParameters(dogRequest, dogFromDataBase);

            Dog saved = dogsRepository.save(dogToBeUpdated);

            log.info("Successful Update! .. Dog was mapped to the database");

            return mapper.map(saved);

        } else {
            log.error("Dog was not found in the database with id : " + id);

            throw new NotFoundException("* Dog with id : " + id + " is not present in the DataBase ");

        }
    }


    public DogResponse updateDogPartially(DogRequest request) {
        Integer id = request.getId();

        Dog dogFromDataBase = dogsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("* Dog with id: " + id + " is not present in the DataBase"));

        setParametersPartially(request, dogFromDataBase);

        dogsRepository.save(dogFromDataBase);

        return mapper.map(dogFromDataBase);
    }


    //helper
    private Dog setAllParameters(DogRequest dogRequest, Optional<Dog> dogFromDataBase) {
        Dog dogToBeUpdated = dogFromDataBase.get();
        dogToBeUpdated.setName(dogRequest.getName());
        dogToBeUpdated.setAge(dogRequest.getAge());
        dogToBeUpdated.setGenealogy(dogRequest.getGenealogy());
        dogToBeUpdated.setHasMicrochip(dogRequest.getHasMicrochip());
        dogToBeUpdated.setChipNumber(dogRequest.getChipNumber());
        return dogToBeUpdated;
    }

    //helper
    private void setParametersPartially(DogRequest request, Dog dogFromDataBase) {
        dogFromDataBase.setName(request.getName() == null ? dogFromDataBase.getName() : request.getName());

        dogFromDataBase.setAge(request.getAge() == null ? dogFromDataBase.getAge() : request.getAge());

        dogFromDataBase.setHasMicrochip(request.getHasMicrochip() == null ?
                dogFromDataBase.getHasMicrochip() : request.getHasMicrochip());

        dogFromDataBase.setChipNumber(request.getChipNumber() == null ?
                dogFromDataBase.getChipNumber() : request.getChipNumber());
    }


}
