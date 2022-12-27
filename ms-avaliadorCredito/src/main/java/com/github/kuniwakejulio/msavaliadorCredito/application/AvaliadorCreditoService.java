package com.github.kuniwakejulio.msavaliadorCredito.application;

import com.github.kuniwakejulio.msavaliadorCredito.application.dto.DadosClienteDto;
import com.github.kuniwakejulio.msavaliadorCredito.domain.model.SituacaoCliente;
import com.github.kuniwakejulio.msavaliadorCredito.infra.feing.ClienteResourceFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    @Autowired
    private final ClienteResourceFeign clienteResourceFeign;

    public SituacaoCliente obterSituacaoCliente(String cpf) {
        // Obter cartoes do cliente MS Cartoes
        // Obter dados do cliente MS Cliente
        ResponseEntity<DadosClienteDto> dadosClienteDto = clienteResourceFeign.getDadosCliente(cpf);
        return SituacaoCliente.builder()
                .dadosCliente(dadosClienteDto.getBody())
                .build();

    }
}
