package com.jobapp.companyms.messaging;

import com.jobapp.companyms.eventDTO.CompanyDeletedEvent;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class CompanyEventPublisher {

    private final RabbitTemplate rabbitTemplate;


    public CompanyEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void publishCompanyDeletedEvent(Long companyId){
        CompanyDeletedEvent event = new CompanyDeletedEvent(companyId);

        rabbitTemplate
                .convertAndSend(
                        RabbitMQConfiguration.COMPANY_EXCHANGE,
                        RabbitMQConfiguration.COMPANY_DELETED_KEY,event,
                        message -> {
                            message.getMessageProperties()
                                    .setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                            return message;
                        }
                );

    }

}
