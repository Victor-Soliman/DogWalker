package main.mappers;

import main.controllers.dto.DogRequest;
import main.controllers.dto.DogResponse;
import main.repository.entities.Dog;
import org.springframework.stereotype.Component;

@Component
public class DogRequestMapper {

    public Dog map(DogRequest dog) {
        return Dog.builder()
                .name(dog.getName())
                .age(dog.getAge())
                .genealogy(dog.getGenealogy())
                .hasMicrochip(dog.getHasMicrochip())
                .chipNumber(dog.getChipNumber())
                .build();
    }
}
