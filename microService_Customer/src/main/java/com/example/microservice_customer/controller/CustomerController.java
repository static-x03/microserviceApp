package com.example.microservice_customer.controller;

import com.example.microservice_customer.Entity.Customer;
import com.example.microservice_customer.Entity.Region;
import com.example.microservice_customer.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    // --------------------Retrieve All Customer -----------------
    @GetMapping
    public ResponseEntity<List<Customer>> listaAllCustomer( @RequestParam(name = "regionId", required = false) Long regionId){
        List<Customer> customers;
        if(regionId == null){
            customers = customerService.finByCustomerAll();
            if(customers.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else {
            Region region = new Region();
            region.setId(regionId);
            customers = customerService.finByCustomerByRegion(region);
            if(customers == null){
                log.error("Customers whit region id {} not found.", regionId);
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(customers);
    }

    // --------------------Retrieve single Customer -----------------
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id){
        log.info("Fetching Customers with id {}",id);
        Customer customer = customerService.getCustomer(id);
        if(customer == null){
            log.error("Customer with id {} not founds",id);
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(customer);
    }

    // --------------------Create Customer -----------------
    @PostMapping
    public ResponseEntity<Customer> createCostumer(@Valid @RequestBody Customer customer, BindingResult result){
        log.info("Creating Customer : {}",customer);
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
        }
        Customer customerDb = customerService.CreateCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerDb);
    }

    // --------------------Update Customer -----------------
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer){
        log.info("Updating Customer with id {}",id);
        Customer currentCustomer = customerService.getCustomer(id);
        if(currentCustomer == null){
            log.error("Unable to Update Customer with id {} not found",id);
            return ResponseEntity.notFound().build();
        }
        customer.setId(id);
        currentCustomer = customerService.updateCustomer(customer);
        return ResponseEntity.ok(currentCustomer);
    }

    // --------------------Delete Customer -----------------
   @DeleteMapping("/{id}")
   public ResponseEntity<Customer> deleteCustomer(@PathVariable(name = "id")Long id ){
        log.info("Fetching & Deleting Customer with id {}",id);
        Customer customerDB = customerService.getCustomer(id);
        if(customerDB == null){
            log.error("Unable to delete. Customer with id {}",id);
            return ResponseEntity.notFound().build();
        }
        customerDB = customerService.deleteCustomer(customerDB);
        return ResponseEntity.ok(customerDB);
   }


    // --------------------Format Errors JsonString -----------------
    private String formatMessage(BindingResult result){
        List<Map<String,String>>errors = result.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String,String> error = new HashMap<>();
                    error.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorCustomers errorCustomerMessage = ErrorCustomers.builder()
                .code("01")
                .listado_errors(errors)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(errorCustomerMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("esto es el string de errores "+jsonString);
        return jsonString;
    }
}
