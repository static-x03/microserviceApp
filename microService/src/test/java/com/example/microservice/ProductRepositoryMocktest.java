package com.example.microservice;

import com.example.microservice.entity.Category;
import com.example.microservice.entity.Product;
import com.example.microservice.repository.IProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

@DataJpaTest
public class ProductRepositoryMocktest {

    @Autowired
    private IProductRepository productRepository;

    @Test
    public void when_finbyCategoryListProduct(){
        Product product = Product.builder()
                .name("computer")
                .category(Category.builder().id(1L).build())
                .description(" ")
                .stock(Double.parseDouble("10"))
                .price(Double.parseDouble("99.00"))
                .status("Created")
                .createdAt(new Date()).build();

        productRepository.save(product);

        List<Product> productList = productRepository
                .findByCategory(product.getCategory());

        Assertions.assertThat(productList.size()).isEqualTo(3);

    }
}
