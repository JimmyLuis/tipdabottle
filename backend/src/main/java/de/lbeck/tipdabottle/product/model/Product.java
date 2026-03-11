package de.lbeck.tipdabottle.product.model;

import de.lbeck.tipdabottle.common.enums.ProductCategory;
import de.lbeck.tipdabottle.container.model.Container;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String price;
    @Column
    private ProductCategory category;
    @Column
    private int stock;
    @OneToOne(cascade = CascadeType.ALL)
    private Container container;

}
