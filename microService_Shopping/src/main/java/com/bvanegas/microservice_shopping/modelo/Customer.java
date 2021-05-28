package com.bvanegas.microservice_shopping.modelo;

import lombok.Data;

@Data
public class Customer {
    private Long id;
    private String numberId;
    private String firstName;
    private String lastName;
    private String email;
    private String photoUrl;
    private Region region;
    private String Status;
}
