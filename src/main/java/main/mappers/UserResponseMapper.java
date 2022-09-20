package main.mappers;

import main.controllers.dto.UserResponse;
import main.repository.entities.User;
import main.repository.entities.UserRole;
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
                .hasDog(user.isHasDog())
                .userBlocked(user.isUserBlocked())
                .userRole(user.getUserRole())
                .dogs(user.getDogs())
//                .walker(user.getWalker())
//                .order(user.getOrder())
                .build();
    }
}
