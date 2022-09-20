package main.repository.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "walkers")
public class Walker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "age")
    private Integer age;

    @Column(name = "password")
    private String password;

    @Column(name = "city")
    private String city;

    @NotBlank
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @NotBlank
    @Column(name = "email")
    private String email;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    @Column(name = "is_available")
    private Boolean isAvailable;
//
//    @OneToOne(cascade = {CascadeType.DETACH,
//            CascadeType.MERGE,
////            CascadeType.PERSIST,
//            CascadeType.REFRESH})
//    @JoinColumn(name = "user_id")
//    private User user;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "walker",
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH})
    private List<Dog> dogs;

    @OneToOne(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name = "order_id")
    private Order order;
}
