package main.mappers;

import main.dto.DogRequest;
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
