package com.example.microservice.services;

import com.example.microservice.entity.Category;
import com.example.microservice.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IProductServices {

    public List<Product> listAllProduct();
    public Product getProduct(Long id);
    public Product createProduct(Product product);
    public Product updateProduct(Product product);
    public Product deleteProduct(Long id);
    public List<Product> finByCategory(Category category);
    public Product updateStock(Long id, Double quanty);
}
