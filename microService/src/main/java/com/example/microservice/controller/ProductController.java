package com.example.microservice.controller;

import com.example.microservice.entity.Category;
import com.example.microservice.entity.Product;
import com.example.microservice.services.IProductServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/products")
public class ProductController {

    @Autowired
    private IProductServices productServices;

    @GetMapping
    public ResponseEntity<List<Product>> listProduct(@RequestParam(name="categoryId",required = false)Long categoryId){
        List<Product>products = new ArrayList<>();
        if(null== categoryId){
            products = productServices.listAllProduct();
            if(products.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else {
            products = productServices.finByCategory(Category.builder().id(categoryId).build());
            if(products.isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> get_product(@PathVariable("id")Long id){
        Product product = productServices.getProduct(id);
        if(null==product){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);

    }


    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult result){
        if(result.hasErrors()){
            System.out.println(this.formatMessage(result));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
        }
        Product productCreate = productServices.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id,@RequestBody Product product){
        product.setId(id);
        Product productUpdate = productServices.updateProduct(product);
        if(productUpdate == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productUpdate);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@RequestParam("id") Long id){
        Product productDelete = productServices.deleteProduct(id);
        if(productDelete == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDelete);
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<Product> UpdateStock(@PathVariable Long id,
           @RequestParam(name="qunanty",required = true) Double quanty){

        Product stockUpdate = productServices.updateStock(id,quanty);
        if(stockUpdate == null){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(stockUpdate);
    }

    private String formatMessage(BindingResult result){
        List<Map<String,String>>errors = result.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String,String> error = new HashMap<>();
                    error.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .listado_errors(errors)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("esto es el string de errores "+jsonString);
        return jsonString;
    }
}
