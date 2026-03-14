package de.lbeck.tipdabottle.product.controller;

import de.lbeck.tipdabottle.product.dto.ProductCreateDTO;
import de.lbeck.tipdabottle.product.dto.ProductDTO;
import de.lbeck.tipdabottle.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ProductDTO createProductWithContainer(@RequestBody ProductCreateDTO productCreateDTO){
        return productService.createProductWithContainer(productCreateDTO);
    }

}
