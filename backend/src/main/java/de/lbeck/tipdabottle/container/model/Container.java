package de.lbeck.tipdabottle.container.model;

import de.lbeck.tipdabottle.common.enums.ProductCategory;
import de.lbeck.tipdabottle.product.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Container {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
