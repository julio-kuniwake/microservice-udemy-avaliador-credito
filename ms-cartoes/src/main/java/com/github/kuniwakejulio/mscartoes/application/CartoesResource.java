package com.github.kuniwakejulio.mscartoes.application;

import com.github.kuniwakejulio.mscartoes.application.dto.CartaoDto;
import com.github.kuniwakejulio.mscartoes.application.dto.CartoesClienteDto;
import com.github.kuniwakejulio.mscartoes.application.form.CartaoForm;
import com.github.kuniwakejulio.mscartoes.domain.Cartao;
import com.github.kuniwakejulio.mscartoes.domain.ClienteCartao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/cartoes")
public class CartoesResource {
    @Autowired
    private final CartaoService cartaoService;
    @Autowired
    private final ClienteCartaoService clienteCartaoService;

    public CartoesResource(CartaoService cartaoService, ClienteCartaoService clienteCartaoService) {
        this.cartaoService = cartaoService;
        this.clienteCartaoService = clienteCartaoService;
    }

    @GetMapping
    public String status() {
        return "Cartoes OK";
    }

    @PostMapping
    public ResponseEntity<CartaoDto> cadastrar(@RequestBody CartaoForm cartaoForm) {
        try {
            Cartao cartao = cartaoForm.convertToModel();
            return this.cartaoService.save(cartao);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Não foi possivel cadatrar cartão!");
        }
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<CartaoDto>> getCartoesRendaAte(@RequestParam("renda") Long renda) {
        try {
            return this.cartaoService.getCartoesRendaMenorIgual(renda);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Não foi possivel listar os cartoes!");
        }
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesClienteDto>> getCartoesByClienteCpf(@RequestParam String cpf) {
        try {
            List<ClienteCartao> listClienteCartao = clienteCartaoService.listCartoesByCpf(cpf);
            List<CartoesClienteDto> listCartoesClienteDto = listClienteCartao.stream()
                    .map(CartoesClienteDto::convertToCartoesClienteDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(listCartoesClienteDto);
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("Não foi possivel lista os cartoes por cliente!");
        }
    }
}
