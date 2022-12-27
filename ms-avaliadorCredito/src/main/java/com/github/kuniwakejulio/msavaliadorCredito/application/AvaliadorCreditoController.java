package com.github.kuniwakejulio.msavaliadorCredito.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/avaliacoes-credito")
public class AvaliadorCreditoController {

    @GetMapping
    public String status(){
        return "OK Avaaaliador Credito";
    }
}
