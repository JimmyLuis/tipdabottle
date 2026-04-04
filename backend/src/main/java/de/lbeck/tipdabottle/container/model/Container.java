package de.lbeck.tipdabottle.container.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @JsonBackReference
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
