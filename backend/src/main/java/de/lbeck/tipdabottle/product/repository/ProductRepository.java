package de.lbeck.tipdabottle.product.repository;

import de.lbeck.tipdabottle.product.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
