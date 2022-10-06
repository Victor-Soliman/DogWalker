package main.mappers;

import main.dto.WalkerRequest;
import main.entities.Walker;
import org.springframework.stereotype.Component;

@Component

public class WalkerRequestMapper {

    public Walker map(WalkerRequest walkerRequest) {
        return Walker.builder()
                .name(walkerRequest.getName())
                .age(walkerRequest.getAge())
                .password(walkerRequest.getPassword())
                .city(walkerRequest.getCity())
                .phoneNumber(walkerRequest.getPhoneNumber())
                .address(walkerRequest.getAddress())
                .email(walkerRequest.getEmail())
                .yearsOfExperience(walkerRequest.getYearsOfExperience())
                .isAvailable(walkerRequest.getIsAvailable())
//                .user(walkerRequest.getUser())
                .build();
    }
}
