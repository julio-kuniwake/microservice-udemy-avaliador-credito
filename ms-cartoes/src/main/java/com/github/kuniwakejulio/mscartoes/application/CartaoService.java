package com.github.kuniwakejulio.mscartoes.application;

import com.github.kuniwakejulio.mscartoes.application.dto.CartaoDto;
import com.github.kuniwakejulio.mscartoes.domain.Cartao;
import com.github.kuniwakejulio.mscartoes.infra.repositories.CartaoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@Service
public class CartaoService {
    private final CartaoRepository cartaoRepository;

    public CartaoService(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @Transactional
    public ResponseEntity<CartaoDto> save(Cartao cartao) {
        cartaoRepository.save(cartao);
        URI uri = UriComponentsBuilder
                .fromPath("/cartoes/{id}")
                .buildAndExpand(cartao.getId())
                .toUri();
        return ResponseEntity.created(uri).body(new CartaoDto(cartao));
    }

    public ResponseEntity<List<CartaoDto>> getCartoesRendaMenorIgual(Long renda) {
        BigDecimal rendaBigDecimal = BigDecimal.valueOf(renda);
        List<Cartao> listCartao = cartaoRepository.findByRendaLessThanEqual(rendaBigDecimal);

        return ResponseEntity.ok(CartaoDto.convertToListCartaoDto(listCartao));
    }

}
