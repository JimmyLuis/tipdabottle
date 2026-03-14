package de.lbeck.tipdabottle.container.model;

import de.lbeck.tipdabottle.common.enums.ProductCategory;
import de.lbeck.tipdabottle.product.model.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Container {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.DETACH)
    private Product product;
    @Column
    private String name;
    @Column
    private String description;
    @Enumerated(EnumType.STRING)
    @Column
    private ProductCategory category;
    @Column
    private int capacity;
    @Column
    private int stock;
}
