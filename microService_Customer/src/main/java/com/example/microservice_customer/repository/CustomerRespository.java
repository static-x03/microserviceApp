package com.example.microservice_customer.repository;

import com.example.microservice_customer.Entity.Customer;
import com.example.microservice_customer.Entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRespository extends JpaRepository<Customer,Long> {
    Customer findByNumberId(String numberId);
    //public List<Customer> finByLastName(String lastname);
    List<Customer> findByRegion(Region region);
}
