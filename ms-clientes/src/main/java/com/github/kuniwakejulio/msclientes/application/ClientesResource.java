package com.github.kuniwakejulio.msclientes.application;

import com.github.kuniwakejulio.msclientes.application.dto.ClienteDto;
import com.github.kuniwakejulio.msclientes.application.dto.DadosCompletoClienteDto;
import com.github.kuniwakejulio.msclientes.application.form.ClienteForm;
import com.github.kuniwakejulio.msclientes.domain.Cliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/clientes")
@Slf4j
public class ClientesResource {

    @Autowired
    private final ClientesService cliService;

    public ClientesResource(ClientesService cliService) {
        this.cliService = cliService;
    }

    @GetMapping
    public String status() {
        log.info("Obtendo o status do microservice de clientes");
        return "OK";
    }

    @PostMapping()
    public ResponseEntity<ClienteDto> save(@RequestBody ClienteForm clienteForm) {
        try {
            Cliente cliente = clienteForm.convertToModel();
            return this.cliService.save(cliente);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Não foi possivel salvar cliente!");
        }
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<DadosCompletoClienteDto> getDadosCliente(@RequestParam String cpf) {
        try {
            return this.cliService.getByCpf(cpf);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Cliente não encontrado!");
        }
    }
}
