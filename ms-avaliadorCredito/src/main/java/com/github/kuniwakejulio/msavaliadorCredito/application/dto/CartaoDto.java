package com.github.kuniwakejulio.msavaliadorCredito.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CartaoDto {
    private Long id;
    private String nome;
    private String bandeira;
    private BigDecimal limiteBasico;
}
