package com.github.kuniwakejulio.msavaliadorCredito.application;

import com.github.kuniwakejulio.msavaliadorCredito.application.dto.CartaoClienteDto;
import com.github.kuniwakejulio.msavaliadorCredito.application.dto.DadosClienteDto;
import com.github.kuniwakejulio.msavaliadorCredito.application.exception.DadosClienteNotFoundException;
import com.github.kuniwakejulio.msavaliadorCredito.application.exception.ErroComunicacaoMicroServiceException;
import com.github.kuniwakejulio.msavaliadorCredito.domain.model.SituacaoCliente;
import com.github.kuniwakejulio.msavaliadorCredito.infra.feing.CartoesResourceFeign;
import com.github.kuniwakejulio.msavaliadorCredito.infra.feing.ClienteResourceFeign;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    @Autowired
    private final ClienteResourceFeign clienteResourceFeign;

    @Autowired
    private final CartoesResourceFeign cartoesResourceFeign;

    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroServiceException {
        try {
            ResponseEntity<DadosClienteDto> dadosClienteDto = clienteResourceFeign.getDadosCliente(cpf); // Obter dados do cliente MS Cliente
            ResponseEntity<List<CartaoClienteDto>> listdadosCartaocliente = cartoesResourceFeign.getCartoesByClienteCpf(cpf); // Obter cartoes do cliente MS Cartoes
            return SituacaoCliente.builder()
                    .dadosCliente(dadosClienteDto.getBody())
                    .cartaoClientes(listdadosCartaocliente.getBody())
                    .build();
        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroServiceException(e.getMessage(), status);
        }
    }
}
