package com.parkway.service;

import com.parkway.dto.Booking;
import com.parkway.dto.User;
import com.parkway.exception.MailVerificationException;
import com.parkway.util.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class MailService {
    @Value("${app.company.name}")
    private String companyName;
    @Value("${spring.mail.username}")
    private String hostMail;
    private JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(User user, String siteURL) {
        try {
            String toAddress = user.getEmail();
            String fromAddress = hostMail;
            String senderName = companyName;
            String subject = companyName + " : Please verify your registration";
            String content = "Dear [[name]],<br>"
                    + "Please click the link below to verify your registration:<br>"
                    + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                    + "Thank you,<br>"
                    + companyName;

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);

            content = content.replace("[[name]]", user.getFirstName() + " " + user.getLastName());
            String verifyURL = siteURL + "/api/auth/verify?code=" + user.getVerificationCode();
            content = content.replace("[[URL]]", verifyURL);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException ex) {
            throw new MailVerificationException(ex);
        }
    }

    public void sendBookingMail(Booking booking) {
        try {
            User user = booking.getUser();
            String toAddress = user.getEmail();
            String fromAddress = hostMail;
            String senderName = companyName;
            String subject = companyName + " : Booking Information";
            String content = "Dear <b>[[name]]</b>,<br>"
                    + "Please Find your recent booking details.<br>"
                    +"------------------------------------------------------<br>"
                    + "<b>Booking Details</b><br>"
                    +"------------------------------------------------------<br>"
                    + "Booking Reference ID : [[bookingId]]<br>"
                    + "Parking Name : [[parkingName]]<br>"
                    + "Parking Location : [[parkingLocation]]<br>"
                    + "Start Time : [[startTime]]<br>"
                    + "End Time : [[endTime]]<br>"
                    + "Cost per Hour : [[cost]] $<br>"
                    + "Total Cost : [[totalCost]] $<br>"
                    + "Booking Time : [[creationTime]]<br>"
                    + "Booking Status : <b>[[bookingStatus]]</b><br>"
                    +"------------------------------------------------------<br>"
                    + "Thank you,<br>"
                    + companyName;

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);

            long totalCost = Util.hoursBetween(booking.getStartTime(), booking.getEndTime()) * booking.getParking().getHourlyPrice();

            content = content.replace("[[name]]", user.getFirstName() + " " + user.getLastName());
            content = content.replace("[[bookingId]]", booking.getBookingId().toString());
            content = content.replace("[[parkingName]]", booking.getParking().getParkingName());
            content = content.replace("[[parkingLocation]]", booking.getParking().getLocation().getLocationName());
            content = content.replace("[[startTime]]", booking.getStartTime().getTime().toString());
            content = content.replace("[[endTime]]", booking.getEndTime().getTime().toString());
            content = content.replace("[[cost]]", String.valueOf(booking.getParking().getHourlyPrice()));
            content = content.replace("[[totalCost]]", String.valueOf(totalCost));
            content = content.replace("[[creationTime]]", booking.getCreatedAt().getTime().toString());
            content = content.replace("[[bookingStatus]]", booking.getBookingStatus().toString());

            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException ex) {
            throw new MailVerificationException(ex);
        }
    }
}
