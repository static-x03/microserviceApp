package com.bvanegas.microservice_shopping.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class MenssajeError {
    private String code ;
    private List<Map<String, String >> messages ;
}
