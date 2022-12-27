package com.github.kuniwakejulio.mscartoes.application.dto;

import com.github.kuniwakejulio.mscartoes.domain.BandeiraCartao;
import com.github.kuniwakejulio.mscartoes.domain.Cartao;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CartaoDto {
    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limiteBasico;

    public CartaoDto(Cartao cartao) {
        this.nome = cartao.getNome();
        this.bandeira = cartao.getBandeira();
        this.renda = cartao.getRenda();
        this.limiteBasico = cartao.getLimiteBasico();
    }

    public CartaoDto(String nome, BandeiraCartao bandeira, BigDecimal renda, BigDecimal limiteBasico) {
        this.nome = nome;
        this.bandeira = bandeira;
        this.renda = renda;
        this.limiteBasico = limiteBasico;
    }

    public static List<CartaoDto> convertToListCartaoDto(List<Cartao> cartoes) {
        return cartoes.stream()
                .map(cartao -> new CartaoDto(cartao.getNome(), cartao.getBandeira(), cartao.getRenda(), cartao.getLimiteBasico()))
                .collect(Collectors.toList());
    }

}
