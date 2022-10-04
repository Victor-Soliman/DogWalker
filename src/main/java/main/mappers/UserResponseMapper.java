package main.mappers;

import main.dto.UserResponse;
import main.repository.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper {

    public UserResponse map(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .password(user.getPassword())
                .city(user.getCity())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .email(user.getEmail())
                .hasDog(user.getHasDog())
                .userBlocked(user.getUserBlocked())
                .userRole(user.getUserRole())
                .build();
    }
}
