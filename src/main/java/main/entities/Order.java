package main.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "days_walked")
    private Integer daysWalked;

    @Column(name = "cost_per_day")
//    @Min(value = 50)
//    @Max(value = 50)
    private Double costPerDay;

    @Column(name = "walk_cost")
    private Double walkCost;

    @Column(name = "dog_walked")
    private Boolean dogWalked;

    @OneToOne
    @JoinColumn(name = "walker_id")
    private Walker walker;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "dog_id")
    private Dog dog;
}
