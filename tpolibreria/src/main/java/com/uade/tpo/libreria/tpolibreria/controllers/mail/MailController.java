package com.uade.tpo.libreria.tpolibreria.controllers.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.uade.tpo.libreria.tpolibreria.controllers.mail.MailRequest;
import com.uade.tpo.libreria.tpolibreria.service.MailService;

@RestController
@RequestMapping("/email")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/send")
    public String sendEmail(@RequestBody MailRequest mailRequest) {
        mailService.sendMail(mailRequest.getTo(), mailRequest.getSubject(), mailRequest.getBody());
        return "Correo enviado exitosamente.";
    }
}
