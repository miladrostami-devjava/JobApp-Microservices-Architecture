package com.jobapp.companyms.messaging;

import com.jobapp.companyms.eventDTO.ReviewCreatedEvent;
import com.jobapp.companyms.eventDTO.ReviewDeletedEvent;
import com.jobapp.companyms.eventDTO.ReviewUpdatedEvent;
import com.jobapp.companyms.service.CompanyService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ReviewEventListener {

private final CompanyService companyService;


    public ReviewEventListener(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RabbitListener(queues = RabbitMQConfiguration.REVIEW_CREATED_QUEUE)
    public void handleReviewCreatedEvent(ReviewCreatedEvent event){
        companyService.updateCompanyRatingOnCreate(event);
    }
    @RabbitListener(queues = RabbitMQConfiguration.REVIEW_UPDATED_QUEUE)
    public void handleReviewUpdatedEvent(ReviewUpdatedEvent event){
        companyService.updateCompanyRatingOnUpdate(event);
    }
    @RabbitListener(queues = RabbitMQConfiguration.REVIEW_DELETED_QUEUE)
    public void handleReviewDeletedEvent(ReviewDeletedEvent event){
        companyService.updateCompanyRatingOnDelete(event);
    }


}
