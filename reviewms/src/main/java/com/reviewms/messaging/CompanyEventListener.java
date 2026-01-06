package com.reviewms.messaging;


import com.reviewms.eventDTO.CompanyDeletedEvent;
import com.reviewms.service.ReviewService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class CompanyEventListener {

    private ReviewService reviewService;

    public CompanyEventListener(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @RabbitListener(queues = RabbitMQConfiguration.COMPANY_DELETED_QUEUE_REVIEWS)
    public void handleCompanyDeletedEvent(CompanyDeletedEvent event) {
        reviewService.deleteByCompanyId(event.getCompanyId());
    }

}

