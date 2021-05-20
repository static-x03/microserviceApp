package com.bvanegas.microservice_shopping.controller;


import com.bvanegas.microservice_shopping.entity.Invoice;
import com.bvanegas.microservice_shopping.service.InvoiceService;
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
@RequestMapping("/invoices")
public class invoiceController {

    private final InvoiceService invoiceService;

    public invoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public ResponseEntity<List<Invoice>> listAllInvoice(){
        List<Invoice> allInvoice = invoiceService.findInvoiceAll();
        if(allInvoice.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allInvoice);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable("id") Long id){
        log.info("Fetching invoice with id {}",id);
        Invoice invoice = invoiceService.getInvoice(id);
        if(invoice == null){
            log.error("Invoice with id{} not found",id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(invoice);
    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@Valid @RequestBody Invoice invoice, BindingResult result){
        log.info("Creating Invoice : {}",invoice);
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
        }
        Invoice invoiceCreate = invoiceService.createInvoice(invoice);
        return ResponseEntity.ok(invoiceCreate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable("id")Long id, @RequestBody Invoice invoice){
        log.info("Updating Invoice with id{}",id);
        invoice.setId(id);
        Invoice updateInvoice = invoiceService.updateInvoice(invoice);
        if(updateInvoice==null){
            log.error("update invoice with id{} not found",id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateInvoice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Invoice> deleteInvoice(@PathVariable("id")Long id){
        log.info("Fetching & Deleting Invoice with");
        Invoice deleteInvoice =invoiceService.getInvoice(id);
        if(deleteInvoice==null){
            log.error("Unable to delete. Invoice with id{} not found",id);
            return ResponseEntity.notFound().build();
        }
        deleteInvoice = invoiceService.deleteInvoice(deleteInvoice);
        return ResponseEntity.ok(deleteInvoice);

    }

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String> error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        MenssajeError errorMessage = MenssajeError.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
