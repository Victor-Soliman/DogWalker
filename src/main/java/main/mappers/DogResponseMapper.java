package main.mappers;

import main.dto.DogResponse;
import main.entities.Dog;
import org.springframework.stereotype.Component;

@Component
public class DogResponseMapper {

    public DogResponse map(Dog dog) {
        return DogResponse.builder()
                .id(dog.getId())
                .name(dog.getName())
                .age(dog.getAge())
                .genealogy(dog.getGenealogy())
                .hasMicrochip(dog.getHasMicrochip())
                .chipNumber(dog.getChipNumber())
                .build();
    }

}
