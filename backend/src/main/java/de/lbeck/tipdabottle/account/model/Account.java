package de.lbeck.tipdabottle.account.model;

import de.lbeck.tipdabottle.account.enums.Role;
import de.lbeck.tipdabottle.customer.model.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    @Column
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    private Customer customer;
}
