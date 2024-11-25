package com.uade.tpo.libreria.tpolibreria.controllers.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.uade.tpo.libreria.tpolibreria.service.MailService;

import jakarta.mail.MessagingException;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/email")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/send")
    public String sendEmail(@RequestBody MailRequest mailRequest) {
        try {
            mailService.sendHtmlMail(mailRequest.getTo(), mailRequest.getSubject(), mailRequest.getBody());
            return "Correo enviado exitosamente.";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error al enviar el correo: " + e.getMessage();
        }
    }
}
