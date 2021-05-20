package com.bvanegas.microservice_shopping.service;

import com.bvanegas.microservice_shopping.entity.Invoice;
import com.bvanegas.microservice_shopping.repository.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class InvoiceImpl implements InvoiceService{

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public List<Invoice> findInvoiceAll() { return invoiceRepository.findAll(); }

    @Override
    public Invoice createInvoice(Invoice invoice) {
        Invoice invoiceDB = invoiceRepository.findByNumberInvoice(invoice.getNumberInvoice());
        if(invoiceDB !=null){
            return invoiceDB;
        }
        invoice.setStatus("Created");
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        Invoice invoiceUpdate = getInvoice(invoice.getId());
        if(invoiceUpdate == null){
            return null;
        }
        invoiceUpdate.setNumberInvoice(invoice.getNumberInvoice());
        invoiceUpdate.setCustomerId(invoice.getCustomerId());
        invoiceUpdate.setDescription(invoice.getDescription());
        invoiceUpdate.getItems().clear();
        invoiceUpdate.setItems(invoice.getItems());

        return invoiceRepository.save(invoiceUpdate);
    }

    @Override
    public Invoice deleteInvoice(Invoice invoice) {
        Invoice invoiceDelete = getInvoice(invoice.getId());
        if(invoiceDelete == null ){
            return null;
        }
        invoiceDelete.setStatus("Deleted");
        return invoiceRepository.save(invoiceDelete);
    }

    @Override
    public Invoice getInvoice(Long id) { return invoiceRepository.findById(id).orElse(null); }
}
