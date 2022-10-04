package main.mappers;

import main.dto.WalkerResponse;
import main.repository.entities.Walker;
import org.springframework.stereotype.Component;

@Component
public class WalkerResponseMapper {

    public WalkerResponse map(Walker walker) {

        return WalkerResponse.builder()
                .id(walker.getId())
                .name(walker.getName())
                .age(walker.getAge())
                .password(walker.getPassword())
                .city(walker.getCity())
                .phoneNumber(walker.getPhoneNumber())
                .address(walker.getAddress())
                .email(walker.getEmail())
                .yearsOfExperience(walker.getYearsOfExperience())
                .isAvailable(walker.getIsAvailable())
                .build();
    }
}
