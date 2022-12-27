package com.github.kuniwakejulio.msavaliadorCredito.infra.feing;

import com.github.kuniwakejulio.msavaliadorCredito.application.dto.CartaoClienteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "mscartoes", path = "/cartoes")
public interface CartoesResourceFeign {

    @GetMapping(params = "cpf")
    ResponseEntity<List<CartaoClienteDto>> getCartoesByClienteCpf(@RequestParam String cpf);
}
