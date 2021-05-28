package com.bvanegas.microservice_shopping.client;

import com.bvanegas.microservice_shopping.modelo.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "customer-service", fallback = CustomerHystrixFallBackFactory.class)
public interface CustomerClient {

    @GetMapping("/customers/{id}")
    ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id);

}
