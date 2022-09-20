package main.repository.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
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

    @Column(name = "walk_cost")
    private Double walk_cost;

    @Column(name = "dog_walked")
    private Boolean dogWalked;

//    @OneToOne(cascade = {CascadeType.DETACH,
//            CascadeType.MERGE,
////            CascadeType.PERSIST,
//            CascadeType.REFRESH})
//    @JoinColumn(name = "walker_id")
//    private Walker walker;
//
//    @OneToOne(cascade = {CascadeType.DETACH,
//            CascadeType.MERGE,
////            CascadeType.PERSIST,
//            CascadeType.REFRESH})
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @OneToOne(cascade = {CascadeType.DETACH,
//            CascadeType.MERGE,
////            CascadeType.PERSIST,
//            CascadeType.REFRESH})
//    @JoinColumn(name = "dog_id")
//    private Dog dog;
}
