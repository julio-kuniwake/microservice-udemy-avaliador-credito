package com.github.kuniwakejulio.mscartoes.application.dto;

import com.github.kuniwakejulio.mscartoes.domain.ClienteCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartoesClienteDto {
    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;

    public static CartoesClienteDto convertToCartoesClienteDto(ClienteCartao model) {
        return new CartoesClienteDto(
                model.getCartao().getNome(),
                model.getCartao().getBandeira().toString(),
                model.getLimite()
        );
    }
}
