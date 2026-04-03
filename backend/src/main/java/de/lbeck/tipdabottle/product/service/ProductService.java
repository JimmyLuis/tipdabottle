package de.lbeck.tipdabottle.product.service;

import de.lbeck.tipdabottle.container.model.Container;
import de.lbeck.tipdabottle.container.repository.ContainerRepository;
import de.lbeck.tipdabottle.product.dto.in.RequestProductUpdateDTO;
import de.lbeck.tipdabottle.product.dto.in.RequestProductCreateDTO;
import de.lbeck.tipdabottle.product.dto.out.ResponseProductPublicDTO;
import de.lbeck.tipdabottle.product.dto.ProductMapper;
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

    public List<Product> getAllProducts(){
        List<Product> responseProductList = new ArrayList<>();
        productRepository.findAll().forEach(responseProductList::add);
        return responseProductList;
    }

    public Product getProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
    }

    public Product createProductWithContainer(RequestProductCreateDTO requestProductCreateDTO){
        Product product = productMapper.toEntity(requestProductCreateDTO);
        Container container = product.getContainer();
        container = containerRepository.save(container);
        product.setContainer(container);
        product = productRepository.save(product);
        container.setProduct(product);
        containerRepository.save(container);
        return product;
    }

    public Product updateProduct(Long id, RequestProductUpdateDTO requestProductUpdateDTO){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
        product = productMapper.toEntity(requestProductUpdateDTO, product);
        return productRepository.save(product);
    }

    public Product updateProductAndContainer(Product product){
        return productRepository.save(product);
    }

}
