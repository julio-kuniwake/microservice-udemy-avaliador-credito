package com.github.kuniwakejulio.msavaliadorCredito.domain.model;

import com.github.kuniwakejulio.msavaliadorCredito.application.dto.CartaoClienteDto;
import com.github.kuniwakejulio.msavaliadorCredito.application.dto.DadosClienteDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class SituacaoCliente {

    private DadosClienteDto dadosCliente;
    private List<CartaoClienteDto> cartaoClientes;

    public SituacaoCliente(DadosClienteDto dadosCliente, List<CartaoClienteDto> cartaoClientes) {
        this.dadosCliente = dadosCliente;
        this.cartaoClientes = cartaoClientes;
    }
}
