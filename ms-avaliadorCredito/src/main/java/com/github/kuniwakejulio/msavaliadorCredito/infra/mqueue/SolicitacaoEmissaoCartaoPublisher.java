package com.github.kuniwakejulio.msavaliadorCredito.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kuniwakejulio.msavaliadorCredito.application.form.DadosSolicitacaoEmissaoCartaoForm;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SolicitacaoEmissaoCartaoPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final Queue queueEmissaoCartoes;

    public void solicitarCartao(DadosSolicitacaoEmissaoCartaoForm dadosForm) throws JsonProcessingException {
        String json = convertToJson(dadosForm);
        rabbitTemplate.convertAndSend(queueEmissaoCartoes.getName(), json);
    }

    // Convertendo o objeto para json
    private String convertToJson(DadosSolicitacaoEmissaoCartaoForm dadosForm) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dadosForm);
    }


}
