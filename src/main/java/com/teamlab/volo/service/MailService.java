package com.teamlab.volo.service;

import com.teamlab.volo.config.JHipsterProperties;
import com.teamlab.volo.domain.User;

import org.apache.commons.lang.CharEncoding;
import org.rythmengine.RythmEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.WordUtils;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Service for sending e-mails.
 * <p>
 * We use the @Async annotation to send e-mails asynchronously.
 * </p>
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";
    private static final String BASE_URL = "baseUrl";

    @Inject
    private JHipsterProperties jHipsterProperties;

    @Inject
    private JavaMailSenderImpl javaMailSender;

    @Inject
    private MessageSource messageSource;

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent e-mail to User '{}'", to);
        } catch (Exception e) {
            log.warn("E-mail could not be sent to user '{}', exception is: {}", to, e.getMessage());
        }
    }

    @Async
    public void sendActivationEmail(User user, String baseUrl) {
        String debugMessage = "Sending activation e-mail to '{}'";
        String templateName = "activationEmail";
        String templateKey = "email.activation.title";

        sendEmail(user, baseUrl, debugMessage, templateName, templateKey);
    }

    @Async
    public void sendCreationEmail(User user, String baseUrl) {
        String debugMessage = "Sending creation e-mail to '{}'";
        String templateName = "creationEmail";
        String templateKey = "email.activation.title";

        sendEmail(user, baseUrl, debugMessage, templateName, templateKey);
    }

    @Async
    public void sendPasswordResetMail(User user, String baseUrl) {
        String debugMessage = "Sending password reset e-mail to '{}'";
        String templateName = "passwordResetEmail";
        String templateKey = "email.reset.title";

        sendEmail(user, baseUrl, debugMessage, templateName, templateKey);
    }

    @Async
    public void sendSocialRegistrationValidationEmail(User user, String provider) {
        String debugMessage = "Sending social registration validation e-mail to '{}'";
        String templateName = "passwordResetEmail";
        String templateKey = "email.reset.title";
        String baseUrl = "";

        sendEmail(user, baseUrl, debugMessage, templateName, templateKey);
    }

    private void sendEmail(User user, String baseUrl, String debugMessage, String templateName, String templateKey) {
        log.debug(debugMessage, user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(USER, user);
        params.put(BASE_URL, baseUrl);
//
//        EmailTemplate template = createEmailTemplateObj("Test subject 111111", "Test body 11111111",
//            TemplateType.TENANT_CREATION);
//
//        RythmEngine engine = new RythmEngine(conf);
//
//
//
//        Context context = new Context(locale);
//        context.setVariable(USER, user);
//        context.setVariable(BASE_URL, baseUrl);
//        String content = templateEngine.process(templateName, context);
//        String subject = messageSource.getMessage(templateKey, null, locale);
//        sendEmail(user.getEmail(), subject, content, false, true);
    }
}
