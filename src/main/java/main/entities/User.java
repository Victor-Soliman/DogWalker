package main.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @Min(15)
    @NotNull
    @Column(name = "age")
    private Integer age;

    @Column(name = "password", nullable = false, unique = true)
    @NotNull
    private String password;

    @Column(name = "city")
    @NotNull
    private String city;

    @NotNull
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "address")
    @NotNull
    private String address;

    @Column(name = "email", nullable = false, unique = true)
    @NotNull
    private String email;

    @Column(name = "has_dog")
    private Boolean hasDog;

    @Column(name = "user_blocked")
    private Boolean userBlocked;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;


//    @OneToMany(fetch = FetchType.LAZY,
//            mappedBy = "user")
//    private List<Dog> dogs;

//    @OneToOne
//    @JoinColumn(name = "walker_id")
//    private Walker walker;
//
//    @OneToOne
//    @JoinColumn(name = "order_id")
//    private Order order;
}
