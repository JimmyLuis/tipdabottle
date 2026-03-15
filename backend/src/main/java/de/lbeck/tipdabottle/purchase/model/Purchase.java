package de.lbeck.tipdabottle.purchase.model;

import de.lbeck.tipdabottle.customer.model.Customer;
import de.lbeck.tipdabottle.product.model.Product;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "purchaseGroup_id")
    private PurchaseGroup purchaseGroup;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @Column
    private Timestamp creationTime;
    @Column
    private double worth;
    @Column
    private int quantity;

}
