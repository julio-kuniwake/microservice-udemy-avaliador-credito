package com.github.kuniwakejulio.mscartoes.infra.mqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmissaoCartaoSubscriber {

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}") // Fica escutando essa fila
    public void receberSolicitacaoEmissao(@Payload String payLoad){
        System.out.println(payLoad);
    }
}
