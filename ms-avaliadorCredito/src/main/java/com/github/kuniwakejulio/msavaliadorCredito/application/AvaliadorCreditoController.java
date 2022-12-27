package com.github.kuniwakejulio.msavaliadorCredito.application;

import com.github.kuniwakejulio.msavaliadorCredito.application.dto.ProtocoloSolicitacaoCartaoDto;
import com.github.kuniwakejulio.msavaliadorCredito.application.exception.DadosClienteNotFoundException;
import com.github.kuniwakejulio.msavaliadorCredito.application.exception.ErroComunicacaoMicroServiceException;
import com.github.kuniwakejulio.msavaliadorCredito.application.exception.ErroSolicitacaoCartaoException;
import com.github.kuniwakejulio.msavaliadorCredito.application.form.DadosSolicitacaoEmissaoCartaoForm;
import com.github.kuniwakejulio.msavaliadorCredito.domain.model.DadosAvaliacao;
import com.github.kuniwakejulio.msavaliadorCredito.domain.model.RetornoAvaliacaoCliente;
import com.github.kuniwakejulio.msavaliadorCredito.domain.model.SituacaoCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/avaliacoes-credito")
public class AvaliadorCreditoController {

    @Autowired
    private final AvaliadorCreditoService avaliadorCreditoService;

    public AvaliadorCreditoController(AvaliadorCreditoService avaliadorCreditoService) {
        this.avaliadorCreditoService = avaliadorCreditoService;
    }

    @GetMapping
    public String status() {
        return "OK Avaaaliador Credito";
    }

    // EndPoint para consulta da situação do cliente
    @GetMapping(value = "/situacao-cliente", params = "cpf")
    public ResponseEntity<Object> consultaSituacaoCliente(@RequestParam("cpf") String cpf) {
        try {
            SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroServiceException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(ResponseEntity.ok(e.getMessage()));
        }
    }

    @PostMapping
    ResponseEntity<Object> realizarAvaliacao(@RequestBody DadosAvaliacao dados) {
        try {
            RetornoAvaliacaoCliente retornoAvaliacaoCliente = avaliadorCreditoService.realizarAvaliacao(dados.getCpf(), dados.getRenda());
            return ResponseEntity.ok(retornoAvaliacaoCliente);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroServiceException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(ResponseEntity.ok(e.getMessage()));
        }
    }

    @PostMapping(value = "/solicitacoes-cartao")
    public ResponseEntity<Object> solicitarCartao(@RequestBody DadosSolicitacaoEmissaoCartaoForm dadosForm) {
        try {
            ProtocoloSolicitacaoCartaoDto protocoloSolicitacaoCartaoDto = avaliadorCreditoService.solicitarEmissaoCartao(dadosForm);
            return ResponseEntity.ok(protocoloSolicitacaoCartaoDto);
        } catch (ErroSolicitacaoCartaoException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
