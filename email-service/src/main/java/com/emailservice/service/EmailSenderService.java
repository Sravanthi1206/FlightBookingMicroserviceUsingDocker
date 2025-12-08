package com.emailservice.service;
import com.emailservice.model.BookingEmailEvent;
import com.emailservice.model.PassengerDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    private final JavaMailSender mailSender;

    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendBookingEmail(BookingEmailEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("Your ticket booking is confirmed!\n\n")
          .append("PNR: ").append(event.getPnr()).append("\n")
          .append("Flight ID: ").append(event.getFlightId()).append("\n")
          .append("Journey Date: ").append(event.getJourneyDate()).append("\n")
          .append("Meal: ").append(event.getMealType()).append("\n")
          .append("Total Amount: ").append(event.getTotalAmount()).append("\n\n")
          .append("Passenger Details:\n");
        for (PassengerDto p : event.getPassengers()) {
            sb.append(" - ").append(p.getName())
              .append(" (").append(p.getGender()).append(") Age: ")
              .append(p.getAge()).append(", Seat: ").append(p.getSeatNumber())
              .append("\n");
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(event.getEmail());
        message.setSubject("Flight Booking Confirmation - PNR " + event.getPnr());
        message.setText(sb.toString());
        mailSender.send(message);
        System.out.println("📨 Email sent to: " + event.getEmail());
    }
}

