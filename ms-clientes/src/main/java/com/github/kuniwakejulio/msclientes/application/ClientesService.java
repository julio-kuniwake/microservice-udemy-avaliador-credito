package com.github.kuniwakejulio.msclientes.application;

import com.github.kuniwakejulio.msclientes.application.dto.ClienteDto;
import com.github.kuniwakejulio.msclientes.application.dto.DadosCompletoClienteDto;
import com.github.kuniwakejulio.msclientes.domain.Cliente;
import com.github.kuniwakejulio.msclientes.infra.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class ClientesService {

    @Autowired
    private final ClienteRepository cliRepository;

    public ClientesService(ClienteRepository cliRepository) {
        this.cliRepository = cliRepository;
    }

    @Transactional
    public ResponseEntity<ClienteDto> save(Cliente cliente) {
        cliRepository.save(cliente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .query("cpf{cpf}")
                .buildAndExpand(cliente.getCpf())
                .toUri();
        return ResponseEntity.created(uri).body(new ClienteDto(cliente));
    }

    public ResponseEntity<DadosCompletoClienteDto> getByCpf(String cpf) {
        Optional<Cliente> clienteOpt = cliRepository.findByCpf(cpf);
        return clienteOpt.map(c -> ResponseEntity.ok(new DadosCompletoClienteDto(c)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
