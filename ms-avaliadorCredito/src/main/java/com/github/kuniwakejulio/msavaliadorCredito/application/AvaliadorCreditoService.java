package com.github.kuniwakejulio.msavaliadorCredito.application;

import com.github.kuniwakejulio.msavaliadorCredito.application.dto.CartaoClienteDto;
import com.github.kuniwakejulio.msavaliadorCredito.application.dto.CartaoDto;
import com.github.kuniwakejulio.msavaliadorCredito.application.dto.DadosClienteDto;
import com.github.kuniwakejulio.msavaliadorCredito.application.exception.DadosClienteNotFoundException;
import com.github.kuniwakejulio.msavaliadorCredito.application.exception.ErroComunicacaoMicroServiceException;
import com.github.kuniwakejulio.msavaliadorCredito.domain.model.CartaoAprovado;
import com.github.kuniwakejulio.msavaliadorCredito.domain.model.RetornoAvaliacaoCliente;
import com.github.kuniwakejulio.msavaliadorCredito.domain.model.SituacaoCliente;
import com.github.kuniwakejulio.msavaliadorCredito.infra.feing.CartoesResourceFeign;
import com.github.kuniwakejulio.msavaliadorCredito.infra.feing.ClienteResourceFeign;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda) throws DadosClienteNotFoundException, ErroComunicacaoMicroServiceException {
        try {
            ResponseEntity<DadosClienteDto> dadosClienteDtoResponse = clienteResourceFeign.getDadosCliente(cpf);
            ResponseEntity<List<CartaoDto>> cartoesRendaAte = cartoesResourceFeign.getCartoesRendaAte(renda);

            List<CartaoDto> cartoes = cartoesRendaAte.getBody();
            List<CartaoAprovado> listaCartoesAprovados = cartoes.stream().map(cartao -> {

                DadosClienteDto dadosClienteDto = dadosClienteDtoResponse.getBody();

                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(dadosClienteDto.getIdade());

                BigDecimal fatorAnaliseLimite = idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fatorAnaliseLimite.multiply(limiteBasico);

                CartaoAprovado cartaoAprovado = new CartaoAprovado();
                cartaoAprovado.setCartao(cartao.getNome());
                cartaoAprovado.setBandeira(cartao.getBandeira());
                cartaoAprovado.setLimiteAprovado(limiteAprovado);

                return cartaoAprovado;
            }).collect(Collectors.toList());

            return new RetornoAvaliacaoCliente(listaCartoesAprovados);

        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroServiceException(e.getMessage(), status);
        }
    }
}
