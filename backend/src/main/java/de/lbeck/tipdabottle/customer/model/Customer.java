package de.lbeck.tipdabottle.customer.model;


import de.lbeck.tipdabottle.customer.dto.CustomerDTO;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column
    private double balance;
    @Column
    private boolean locked;
    @Column
    private boolean community;
    @Column
    private boolean activeProfile;

}


