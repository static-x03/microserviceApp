package com.bvanegas.microservice_shopping.repository;

import com.bvanegas.microservice_shopping.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    List<Invoice> findByCustomerId(Long id);
    Invoice findByNumberInvoice(String numberInvoice);

}
