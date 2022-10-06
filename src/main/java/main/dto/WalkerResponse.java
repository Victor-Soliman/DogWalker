package main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class WalkerResponse {

    private Integer id;

    private String name;

    private Integer age;

    private String password;

    private String city;

    private String phoneNumber;

    private String address;

    private String email;

    private Integer yearsOfExperience;

    private Boolean isAvailable;

}
