package com.github.kuniwakejulio.msavaliadorCredito.application.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoClienteDto {

    private String nome;
    private String banmdeira;
    private BigDecimal limiteLiberado;
}
