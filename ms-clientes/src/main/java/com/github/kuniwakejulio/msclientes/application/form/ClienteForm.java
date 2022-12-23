package com.github.kuniwakejulio.msclientes.application.form;

import com.github.kuniwakejulio.msclientes.domain.Cliente;
import lombok.Data;

@Data
public class ClienteForm {
    private String cpf;
    private String nome;
    private Integer idade;

    public Cliente convertToModel() {
        return new Cliente(cpf, nome, idade);
    }
}
