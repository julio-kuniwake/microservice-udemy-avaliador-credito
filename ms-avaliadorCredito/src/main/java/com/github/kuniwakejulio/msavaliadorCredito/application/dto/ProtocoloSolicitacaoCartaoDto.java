package com.github.kuniwakejulio.msavaliadorCredito.application.dto;

import lombok.Data;

@Data
public class ProtocoloSolicitacaoCartaoDto {
    private String protocolo;

    public ProtocoloSolicitacaoCartaoDto(String protocolo) {
        this.protocolo = protocolo;
    }
}
