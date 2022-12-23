package com.github.kuniwakejulio.msclientes.application.dto;

import com.github.kuniwakejulio.msclientes.domain.Cliente;
import lombok.Data;

@Data
public class ClienteDto {
    private String nome;
    private String cpf;

    public ClienteDto(Cliente cliente){
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
    }

}
