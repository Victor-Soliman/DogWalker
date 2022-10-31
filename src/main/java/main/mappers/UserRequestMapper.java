package main.mappers;

import main.dto.UserRequest;
import main.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserRequestMapper {

    public User map(UserRequest userRequest) {
        return User.builder()
                .name(userRequest.getName())
                .age(userRequest.getAge())
                .password(userRequest.getPassword())
                .city(userRequest.getCity())
                .phoneNumber(userRequest.getPhoneNumber())
                .address(userRequest.getAddress())
                .email(userRequest.getEmail())
                .hasDog(userRequest.getHasDog())
                .userBlocked(userRequest.getUserBlocked())
                .userRole(userRequest.getUserRole())
//                .dogs(userRequest.getDogs())
//                .walker(userRequest.getWalker())
//                .order(userRequest.getOrder())
                .build();
    }
}
