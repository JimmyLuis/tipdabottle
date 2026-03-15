package de.lbeck.tipdabottle.product.controller;

import de.lbeck.tipdabottle.product.dto.ProductCreateDTO;
import de.lbeck.tipdabottle.product.dto.ProductDTO;
import de.lbeck.tipdabottle.product.dto.ProductUpdateDTO;
import de.lbeck.tipdabottle.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PostMapping
    public ProductDTO createProductWithContainer(@Valid @RequestBody ProductCreateDTO productCreateDTO){
        return productService.createProductWithContainer(productCreateDTO);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@Valid @RequestBody ProductUpdateDTO productUpdateDTO, @PathVariable Long id){
        return productService.updateProduct(id, productUpdateDTO);
    }

}
