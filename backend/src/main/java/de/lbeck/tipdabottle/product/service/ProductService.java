package de.lbeck.tipdabottle.product.service;

import de.lbeck.tipdabottle.product.dto.ProductDTO;
import de.lbeck.tipdabottle.product.dto.ProductMapper;
import de.lbeck.tipdabottle.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {


    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductDTO> getAllProducts(){
        List<ProductDTO> productDTOList = new ArrayList<>();
        productRepository.findAll().forEach(product -> productDTOList.add(productMapper.toDTO(product)));
        return productDTOList;
    }


}
