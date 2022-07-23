package com.feng.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.Executor;

@Component
public class EMailUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(EMailUtil.class);

    @Value("${spring.mail.from}")
    private String from;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    @Qualifier(value = "asyncServiceExecutor")
    private Executor executor;

    public void sendEmail(String to, String cc, String subject, String content) {
//        if ("".equals(cc) || " ".equals(cc) || null == cc) {
//            cc = to;
//        }
        String finalCc = cc;
        executor.execute(() -> setMailSender(to, finalCc, subject, content));
    }

    private void setMailSender(String to, String cc, String subject, String content) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(to);
        msg.setCc(cc);
        msg.setSubject(subject);
        msg.setText(content);
        mailSender.send(msg);
    }

}
