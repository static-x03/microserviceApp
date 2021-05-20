package com.example.microservice_customer.service;

import com.example.microservice_customer.Entity.Customer;
import com.example.microservice_customer.Entity.Region;

import java.util.List;

public interface CustomerService   {

    List<Customer> finByCustomerAll();
    List<Customer> finByCustomerByRegion(Region region);
    Customer CreateCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    Customer deleteCustomer(Customer customer);
    Customer getCustomer(Long id);
}
