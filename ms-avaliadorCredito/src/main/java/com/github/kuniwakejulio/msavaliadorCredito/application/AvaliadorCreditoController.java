package com.github.kuniwakejulio.msavaliadorCredito.application;

import com.github.kuniwakejulio.msavaliadorCredito.application.exception.DadosClienteNotFoundException;
import com.github.kuniwakejulio.msavaliadorCredito.application.exception.ErroComunicacaoMicroServiceException;
import com.github.kuniwakejulio.msavaliadorCredito.domain.model.SituacaoCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
