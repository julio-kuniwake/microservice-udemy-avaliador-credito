package com.github.kuniwakejulio.msavaliadorCredito.infra.feing;

import com.github.kuniwakejulio.msavaliadorCredito.application.dto.DadosClienteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "msclientes", path = "/clientes")
public interface ClienteResourceFeign {

    @GetMapping(params = "cpf")
    ResponseEntity<DadosClienteDto> getDadosCliente(@RequestParam String cpf);
}
