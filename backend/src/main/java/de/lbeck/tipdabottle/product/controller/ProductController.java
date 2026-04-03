package de.lbeck.tipdabottle.product.controller;

import de.lbeck.tipdabottle.common.annotations.View;
import de.lbeck.tipdabottle.product.dto.in.RequestProductUpdateDTO;
import de.lbeck.tipdabottle.product.dto.in.RequestProductCreateDTO;
import de.lbeck.tipdabottle.product.dto.out.ResponseProductPublicDTO;
import de.lbeck.tipdabottle.product.model.Product;
import de.lbeck.tipdabottle.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @View(Product.class)
    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @View(Product.class)
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @View(Product.class)
    @PostMapping
    public Product createProductWithContainer(@Valid @RequestBody RequestProductCreateDTO requestProductCreateDTO){
        return productService.createProductWithContainer(requestProductCreateDTO);
    }

    @View(Product.class)
    @PutMapping("/{id}")
    public Product updateProduct(@Valid @RequestBody RequestProductUpdateDTO requestProductUpdateDTO, @PathVariable Long id){
        return productService.updateProduct(id, requestProductUpdateDTO);
    }

}
