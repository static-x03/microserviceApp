package com.example.microservice.services;

import com.example.microservice.entity.Category;
import com.example.microservice.entity.Product;
import com.example.microservice.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImp implements  IProductServices{

    @Autowired
    private IProductRepository productRepository;

    @Override
    public List<Product> listAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        product.setStatus("created");
        product.setCreatedAt(new Date());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product product_db = getProduct(product.getId());
        if(null==product_db ){
            return null;
        }

        product_db.setName(product.getName());
        product_db.setDescription(product.getDescription());
        product_db.setCategory(product.getCategory());
        product_db.setPrice(product.getPrice());
        return productRepository.save(product_db);
    }

    @Override
    public Product deleteProduct(Long id) {
        Product product_db = getProduct(id);
        if(null==product_db ){
            return null;
        }
        product_db.setStatus("Delete");
        return productRepository.save(product_db);
    }

    @Override
    public List<Product> finByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product updateStock(Long id, Double quanty) {
        Product product_db = getProduct(id);
        if(null==product_db ){
            return null;
        }

        Double stock = quanty + product_db.getStock();
        product_db.setStock(stock);
        return productRepository.save(product_db);
    }
}
