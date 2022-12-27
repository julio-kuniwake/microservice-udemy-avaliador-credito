package com.github.kuniwakejulio.mscartoes.application.form;

import com.github.kuniwakejulio.mscartoes.domain.BandeiraCartao;
import com.github.kuniwakejulio.mscartoes.domain.Cartao;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoForm {
    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limite;

    public Cartao convertToModel() {
        return new Cartao(nome, bandeira, renda, limite);
    }

}
