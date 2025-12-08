package com.emailservice.kafka;


import com.emailservice.model.BookingEmailEvent;
import com.emailservice.service.EmailSenderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BookingEventConsumer {

    private final EmailSenderService emailSenderService;

    public BookingEventConsumer(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @KafkaListener(topics = "${booking.kafka.topic}", groupId = "email-group")
    public void consume(BookingEmailEvent event) {
        System.out.println("📧 Received booking event for PNR: " + event.getPnr());
        emailSenderService.sendBookingEmail(event);
    }
}

