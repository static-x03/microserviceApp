package com.example.microservice.repository;

import com.example.microservice.entity.Category;
import com.example.microservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findByCategory(Category category);
}
