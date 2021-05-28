package com.bvanegas.microservice_shopping.client;

import com.bvanegas.microservice_shopping.modelo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value="/products")
@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/{id}")
    public ResponseEntity<Product> get_product(@PathVariable("id")Long id);

    @GetMapping("/{id}/stock")
    public ResponseEntity<Product> UpdateStock(@PathVariable Long id,
                                               @RequestParam(name="quanty",required = true) Double quanty);
}
