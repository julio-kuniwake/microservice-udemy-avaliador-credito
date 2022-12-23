package com.github.kuniwakejulio.msclientes.application.dto;

import com.github.kuniwakejulio.msclientes.domain.Cliente;
import lombok.Data;

@Data
public class DadosCompletoClienteDto {
    private Long id;
    private String cpf;
    private String nome;
    private Integer idade;


    public DadosCompletoClienteDto(Cliente cliente){
        this.id = cliente.getId();
        this.cpf = cliente.getCpf();
        this.nome = cliente.getNome();
        this.idade = cliente.getIdade();
    }

}
