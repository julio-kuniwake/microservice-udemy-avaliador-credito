package com.github.kuniwakejulio.mscartoes.application;

import com.github.kuniwakejulio.mscartoes.application.dto.CartaoDto;
import com.github.kuniwakejulio.mscartoes.application.form.CartaoForm;
import com.github.kuniwakejulio.mscartoes.domain.Cartao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cartoes")
public class CartoesResource {
    private final CartaoService cartaoService;

    public CartoesResource(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
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
//        List<Cartao> listCartoes = cartaoService.getCartoesRendaMenorIgual(renda);
//        return ResponseEntity.ok(listCartoes);
    }
}
