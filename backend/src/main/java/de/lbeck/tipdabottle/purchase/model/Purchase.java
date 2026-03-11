package de.lbeck.tipdabottle.purchase.model;

import de.lbeck.tipdabottle.customer.model.Customer;
import de.lbeck.tipdabottle.product.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private long purchaseGroupId;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @Column
    private Date creationDate;
    @Column
    private double worth;
    @Column
    private int quantity;

}
