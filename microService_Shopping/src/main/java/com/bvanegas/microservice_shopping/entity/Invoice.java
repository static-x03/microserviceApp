package com.bvanegas.microservice_shopping.entity;

import com.bvanegas.microservice_shopping.modelo.Customer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "tb_invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_invoice")
    private String numberInvoice;

    private String description;

    @Column(name="customer_id")
    private Long customerId;

    @Column(name="create_at")
    @Temporal(TemporalType.DATE)
    private Date creatAt;

    @Valid
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private List<InvoiceItem> items;

    private String status;

    @Transient
    private Customer customer;

    public Invoice(){items = new ArrayList<>(); }

    @PrePersist
    public void prePersist(){this.creatAt = new Date();}

}
