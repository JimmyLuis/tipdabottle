package de.lbeck.tipdabottle.product.dto.viewstrategy;

import de.lbeck.tipdabottle.common.viewstrategy.ViewStrategy;
import de.lbeck.tipdabottle.product.dto.ProductMapper;
import de.lbeck.tipdabottle.product.model.Product;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class ProductPublicStrategy implements ViewStrategy<Product> {

    private final ProductMapper productMapper;

    public ProductPublicStrategy(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public boolean supports(Authentication auth, Product entity) {
        return true;
    }

    @Override
    public Object map(Product entity) {
        return productMapper.toPublicDTO(entity);
    }

    @Override
    public Class<Product> getType() {
        return Product.class;
    }
}
