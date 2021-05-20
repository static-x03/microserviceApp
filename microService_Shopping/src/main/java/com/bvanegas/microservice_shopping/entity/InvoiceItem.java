package com.bvanegas.microservice_shopping.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@Data
@Table(name = "tb_invoice_item")
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive(message = "el Stock debe ser mayor a 0")
    private Double quanty;

    private Double price;

    @Column(name = "product_id")
    private Long productId;

    @Transient
    private Double subTotal;

    public Double getSubtotal(){

        if(this.price>0 && this.quanty>0){
            return this.quanty * this.price;
        }else {
            return (double) 0;
        }
    }

    public InvoiceItem(){
        this.quanty = (double) 0;
        this.price = (double) 0;
    }
}
