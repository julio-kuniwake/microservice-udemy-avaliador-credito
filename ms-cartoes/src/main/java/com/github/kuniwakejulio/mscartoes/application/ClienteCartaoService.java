package com.github.kuniwakejulio.mscartoes.application;

import com.github.kuniwakejulio.mscartoes.domain.ClienteCartao;
import com.github.kuniwakejulio.mscartoes.infra.repositories.ClienteCartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteCartaoService {
    @Autowired
    private final ClienteCartaoRepository clienteCartaoRepository;

    ClienteCartaoService(ClienteCartaoRepository clienteCartaoRepository) {
        this.clienteCartaoRepository = clienteCartaoRepository;
    }

    public List<ClienteCartao> listCartoesByCpf(String cpf) {
        return clienteCartaoRepository.findByCpf(cpf);
    }

}
