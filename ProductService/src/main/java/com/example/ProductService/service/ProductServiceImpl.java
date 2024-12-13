package com.example.ProductService.service;

import com.example.ProductService.entity.Product;
import com.example.ProductService.exception.ProductServiceCustomException;
import com.example.ProductService.model.ProductRequest;
import com.example.ProductService.model.ProductResponse;
import com.example.ProductService.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class ProductServiceImpl implements ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    private ProductRepository productRepository;


    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding product...");
        Product product = Product.builder().productName(productRequest.getProductName())
                .price(productRequest.getPrice()).quantity(productRequest.getQuantity()).build();
        productRepository.save(product);
        return product.getId();
    }

    @Override
    public ProductResponse getProductById(long id) {
        log.info("Getting product for productId: {}", id);

        Product product = productRepository.findById(id).orElseThrow(() ->
                new ProductServiceCustomException("product with id:"+id+"is not found","PRODUCT_NOT_FOUND"));

        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(product,productResponse);
        return productResponse;
    }

    @Override
    public void reduceQuantity(long id, long quantity) {
        log.info("Reducing quantity {} for productId: {}", quantity, id);
    Product product = productRepository.findById(id).orElseThrow(() -> new ProductServiceCustomException("product with id:"+id+"is not found","PRODUCT_NOT_FOUND"));
    if(product.getQuantity()<quantity){
        throw new ProductServiceCustomException("Product is not available of that quantity","INSUFFICIENT_QUANTITY");
    }
    product.setQuantity(product.getQuantity()-quantity);
    productRepository.save(product);
    }

}
