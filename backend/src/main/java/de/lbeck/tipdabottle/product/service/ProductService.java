package de.lbeck.tipdabottle.product.service;

import de.lbeck.tipdabottle.container.model.Container;
import de.lbeck.tipdabottle.container.repository.ContainerRepository;
import de.lbeck.tipdabottle.product.dto.ProductCreateDTO;
import de.lbeck.tipdabottle.product.dto.ProductDTO;
import de.lbeck.tipdabottle.product.dto.ProductMapper;
import de.lbeck.tipdabottle.product.dto.ProductUpdateDTO;
import de.lbeck.tipdabottle.product.exception.ProductNotFoundException;
import de.lbeck.tipdabottle.product.model.Product;
import de.lbeck.tipdabottle.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {


    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ContainerRepository containerRepository;//strong dependency

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, ContainerRepository containerRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.containerRepository = containerRepository;
    }

    public List<ProductDTO> getAllProducts(){
        List<ProductDTO> productDTOList = new ArrayList<>();
        productRepository.findAll().forEach(product -> productDTOList.add(productMapper.toDTO(product)));
        return productDTOList;
    }

    public ProductDTO getProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
        return productMapper.toDTO(product);
    }

    public ProductDTO createProductWithContainer(ProductCreateDTO productCreateDTO){
        Product product = productMapper.toEntity(productCreateDTO);
        Container container = product.getContainer();
        container= containerRepository.save(container);
        product.setContainer(container);
        product = productRepository.save(product);
        container.setProduct(product);
        containerRepository.save(container);
        return productMapper.toDTO(product);
    }

    public ProductDTO updateProduct(Long id, ProductUpdateDTO productUpdateDTO){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
        product = productMapper.toEntity(productUpdateDTO, product);
        product = productRepository.save(product);
        return productMapper.toDTO(product);
    }

    public ProductDTO updateProductAndContainer(Product product){
        return productMapper.toDTO(productRepository.save(product));
    }

}
