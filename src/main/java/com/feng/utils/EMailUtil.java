package com.feng.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Component
public class EMailUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(EMailUtil.class);

    @Value("${spring.mail.from}")
    private String from;
    @Value("${temp.file.upload-path}")
    private String uploadPath;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    @Qualifier(value = "asyncServiceExecutor")
    private Executor executor;

    public void sendEmail(String to, String cc, String subject, String content) {
        executor.execute(() -> setMailSender(to, dealCC(to, cc), subject, content));
    }

    public void sendEmail(String to, String cc, String subject, String content, MultipartFile[] files) {
        File[] tempFiles = new File[files.length];
        if (files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                File tempFile = new File(uploadPath + File.separator + file.getOriginalFilename());
                try {
                    file.transferTo(tempFile);
                    tempFiles[i] = tempFile;
                } catch (Exception e) {
                    LOGGER.error("", e);
                }
            }
            Future<?> submit = ((ThreadPoolTaskExecutor) executor).submit(() -> setMailSender(to, dealCC(to, cc), subject, content, tempFiles));
            while (!submit.isDone()) {
                try {
                    TimeUnit.MILLISECONDS.sleep(50);
                } catch (InterruptedException e) {
                    LOGGER.error("", e);
                }
            }
            boolean isDelete = true;
            for (File file : tempFiles) {
                isDelete &= file.delete();
            }
            LOGGER.info("临时文件是否被删除?{}", isDelete);
        }
    }

    private String dealCC(String to, String cc) {
        return "".equals(cc) || " ".equals(cc) || null == cc ? to : cc;
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

    private void setMailSender(String to, String cc, String subject, String content, File[] files) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setCc(cc);
            helper.setSubject(subject);
            helper.setText(content, true);
            if (files.length > 0) {
                for (File file : files) {
                    helper.addAttachment(file.getName(), file);
                }
            }
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }
}
