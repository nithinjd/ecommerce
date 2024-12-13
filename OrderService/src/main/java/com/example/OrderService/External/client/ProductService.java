package com.example.OrderService.External.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="PRODUCTSERVICE")
public interface ProductService {
    @PutMapping("/products/reducedQuantity/{id}")
     ResponseEntity<Void> reducedQuantity(@PathVariable("id") long productId, @RequestParam long reducedQuantity);

    }
