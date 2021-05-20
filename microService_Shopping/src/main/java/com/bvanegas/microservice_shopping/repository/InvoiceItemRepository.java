package com.bvanegas.microservice_shopping.repository;

import com.bvanegas.microservice_shopping.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem,Long> {
}
