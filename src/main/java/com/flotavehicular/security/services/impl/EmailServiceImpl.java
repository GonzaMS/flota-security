package com.flotavehicular.security.services.impl;

import com.flotavehicular.security.enums.EmailTemplateName;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(
            String to,
            String username,
            EmailTemplateName emailTemplateName,
            String link,
            String activationCode,
            String subject
    ) throws MessagingException {
        // Define el nombre del template
        String templateName = emailTemplateName != null ? emailTemplateName.name() : "confirm-email";

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                UTF_8.name()
        );

        // Agrega las propiedades al contexto de Thymeleaf
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);

        if (emailTemplateName == EmailTemplateName.FORGOT_PASSWORD) {
            properties.put("resetPasswordUrl", link); // Usar resetPasswordUrl para reset password
        } else {
            properties.put("confirmationUrl", link); // Usar confirmationUrl para activate account
        }

        properties.put("activationCode", activationCode);

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom("flotavehicular@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);

        // Procesa el template con Thymeleaf
        String template = templateEngine.process(templateName, context);

        helper.setText(template, true);

        // Envia el correo
        mailSender.send(mimeMessage);
    }
}
