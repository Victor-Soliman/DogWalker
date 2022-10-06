package main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.entities.UserRole;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserResponse {

    private Integer id;

    private String name;

    private Integer age;

    private String password;

    private String city;

    private String phoneNumber;

    private String address;

    private String email;

    private Boolean hasDog;

    private Boolean userBlocked;

    private UserRole userRole;


}
