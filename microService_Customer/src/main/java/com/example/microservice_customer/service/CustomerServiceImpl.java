package com.example.microservice_customer.service;

import com.example.microservice_customer.Entity.Customer;
import com.example.microservice_customer.Entity.Region;
import com.example.microservice_customer.repository.CustomerRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService{

    final
    CustomerRespository customerRespository;

    public CustomerServiceImpl(CustomerRespository customerRespository) {
        this.customerRespository = customerRespository;
    }

    @Override
    public List<Customer> finByCustomerAll() {
        return customerRespository.findAll();
    }

    @Override
    public List<Customer> finByCustomerByRegion(Region region) {
        return customerRespository.findByRegion(region);
    }

    @Override
    public Customer CreateCustomer(Customer customer) {
        Customer customerCreate = customerRespository.findByNumberId(customer.getNumberId());
        if(customerCreate !=null){
            return customerCreate;
        }
        customer.setStatus("Created");
        customerCreate = customerRespository.save(customer);
        return customerCreate;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer customerDB = getCustomer(customer.getId());
        if(customerDB == null){
            return null;
        }
        customerDB.setFirstName(customer.getFirstName());
        customerDB.setLastName(customer.getLastName());
        customer.setEmail(customer.getEmail());
        customer.setPhotoUrl(customer.getPhotoUrl());
        return customerRespository.save(customer);
    }

    @Override
    public Customer deleteCustomer(Customer customer) {
        Customer customerDb = getCustomer(customer.getId());
        if(customerDb == null){
            return null;
        }
        customer.setStatus("Deleted");
        return customerRespository.save(customer);
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRespository.findById(id).orElse(null);
    }
}
