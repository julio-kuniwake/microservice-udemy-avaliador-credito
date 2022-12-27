package com.github.kuniwakejulio.mscartoes.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kuniwakejulio.mscartoes.application.dto.DadosSolicitacaoEmissaoCartaoDto;
import com.github.kuniwakejulio.mscartoes.domain.Cartao;
import com.github.kuniwakejulio.mscartoes.domain.ClienteCartao;
import com.github.kuniwakejulio.mscartoes.infra.repositories.CartaoRepository;
import com.github.kuniwakejulio.mscartoes.infra.repositories.ClienteCartaoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmissaoCartaoSubscriber {
    private final CartaoRepository cartaoRepository;
    private final ClienteCartaoRepository clienteCartaoRepository;

    public EmissaoCartaoSubscriber(CartaoRepository cartaoRepository, ClienteCartaoRepository clienteCartaoRepository) {
        this.cartaoRepository = cartaoRepository;
        this.clienteCartaoRepository = clienteCartaoRepository;
    }

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}") // Fica escutando essa fila
    public void receberSolicitacaoEmissao(@Payload String payLoadJson) {
        try {
            var mapper = new ObjectMapper();
            DadosSolicitacaoEmissaoCartaoDto dadosDto = mapper.readValue(payLoadJson, DadosSolicitacaoEmissaoCartaoDto.class); // Transformando o payLoad para Objeto
            Cartao cartao = cartaoRepository.findById(dadosDto.getIdCartao()).orElseThrow();

            ClienteCartao clienteCartao = new ClienteCartao();
            clienteCartao.setCartao(cartao);
            clienteCartao.setCpf(dadosDto.getCpf());
            clienteCartao.setLimite(dadosDto.getLimiteLiberado());

            clienteCartaoRepository.save(clienteCartao); // Efetuando a persistencia

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
