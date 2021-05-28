package com.bvanegas.microservice_shopping.client;

import com.bvanegas.microservice_shopping.modelo.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerHystrixFallBackFactory implements CustomerClient{
    @Override
    public ResponseEntity<Customer> getCustomer(Long id) {
        Customer customer = Customer.builder()
                .firstName("none")
                .lastName("none")
                .email("none")
                .photoUrl("none").build();

        return ResponseEntity.ok(customer);
    }
}
