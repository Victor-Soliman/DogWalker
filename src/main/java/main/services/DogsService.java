package main.services;

import lombok.RequiredArgsConstructor;
import main.controllers.dto.DogRequest;
import main.controllers.dto.DogResponse;
import main.controllers.dto.UserResponse;
import main.mappers.DogRequestMapper;
import main.mappers.DogResponseMapper;
import main.repository.DogsRepository;
import main.repository.entities.Dog;
//import main.repository.entities.Walker;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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
        return mapper.map(dogsRepository.findById(id).get());
    }


    public List<DogResponse> findByName(String name) {
        return dogsRepository.findByName(name).stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public DogResponse save(DogRequest dogRequest) {
        Dog mappedDog = requestMapper.map(dogRequest);
        dogsRepository.save(mappedDog);
        return mapper.map(mappedDog);
    }

    public void delete(Integer id) {
        dogsRepository.deleteById(id);
    }

    public DogResponse update(Integer id, DogRequest dogRequest) {

        Optional<Dog> dogFromDataBase = dogsRepository.findById(id);
        if (dogFromDataBase.isPresent()) {
            Dog dogToBeUpdated = dogFromDataBase.get();
            dogToBeUpdated.setName(dogRequest.getName());
            dogToBeUpdated.setAge(dogRequest.getAge());
            dogToBeUpdated.setGenealogy(dogRequest.getGenealogy());
            dogToBeUpdated.setHasMicrochip(dogRequest.getHasMicrochip());
            dogToBeUpdated.setChipNumber(dogRequest.getChipNumber());

            Dog saved = dogsRepository.save(dogToBeUpdated);
            return mapper.map(saved);

        } else {
            throw new UnsupportedOperationException("Dog with id: " + id + " is not present in the DataBase ");

        }
    }

    public DogResponse updateMicrochipStatus(Integer id, String microchip) {
        Dog dogFromDataBase = dogsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No dog found with id : " + id));

        dogFromDataBase.setHasMicrochip(Boolean.valueOf(microchip));
        Dog saved = dogsRepository.save(dogFromDataBase);

        return mapper.map(saved);
    }

    public DogResponse updateAge(Integer id, Integer age) {
        Dog dogFromDataBase = dogsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No dog found with id : " + id));

        dogFromDataBase.setAge(age);
        Dog saved = dogsRepository.save(dogFromDataBase);

        return mapper.map(saved);
    }

//    public void save(DogRequest dogRequest) {
//        Dog dog = dogRequestMapper.map(dogRequest);
//        dogsRepository.save(dog);
//    }
//
//    public void delete(DogRequest dogRequest) {
//        Dog dog = dogRequestMapper.map(dogRequest);
//        dogsRepository.delete(dog);
//    }


}
