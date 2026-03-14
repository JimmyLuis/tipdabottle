package de.lbeck.tipdabottle.product.model;

import de.lbeck.tipdabottle.common.enums.ProductCategory;
import de.lbeck.tipdabottle.container.model.Container;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private double price;
    @Enumerated(EnumType.STRING)
    @Column
    private ProductCategory category;
    @Column
    private int stock;

    @OneToOne(cascade = CascadeType.DETACH)
    private Container container;

}
