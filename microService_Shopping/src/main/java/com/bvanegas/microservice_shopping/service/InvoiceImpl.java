package com.bvanegas.microservice_shopping.service;

import com.bvanegas.microservice_shopping.client.CustomerClient;
import com.bvanegas.microservice_shopping.client.ProductClient;
import com.bvanegas.microservice_shopping.entity.Invoice;
import com.bvanegas.microservice_shopping.entity.InvoiceItem;
import com.bvanegas.microservice_shopping.modelo.Customer;
import com.bvanegas.microservice_shopping.modelo.Product;
import com.bvanegas.microservice_shopping.repository.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InvoiceImpl implements InvoiceService{

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private ProductClient productClient;
    @Override
    public List<Invoice> findInvoiceAll() { return invoiceRepository.findAll(); }

    @Override
    public Invoice createInvoice(Invoice invoice) {
        Invoice invoiceDB = invoiceRepository.findByNumberInvoice(invoice.getNumberInvoice());
        if(invoiceDB !=null){
            return invoiceDB;
        }
        invoice.setStatus("Created");
        invoiceDB = invoiceRepository.save(invoice);
        invoiceDB.getItems().forEach(invoiceItem -> productClient.UpdateStock(invoiceItem.getProductId(),
                invoiceItem.getQuanty() * -1));

        return invoiceDB;
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
    public Invoice getInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElse(null);
        if(invoice !=null){
            Customer customer = customerClient.getCustomer(invoice.getCustomerId()).getBody();
            invoice.setCustomer(customer);
            List<InvoiceItem>listItem = invoice.getItems().stream().
                    peek(invoiceItem -> {
                        Product product = productClient.get_product(invoiceItem.getProductId()).getBody();
                        invoiceItem.setProduct(product);
                    }).collect(Collectors.toList());
            invoice.setItems(listItem);
        }
        return invoice;
    }
}
