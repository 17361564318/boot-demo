package com.feng.controller;


import com.feng.response.Result;
import com.feng.response.StatusCode;
import com.feng.utils.EMailUtil;
import com.feng.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class EmailController {

    @Autowired
    private EMailUtil eMailUtil;

    @PostMapping("/sendEmail")
    public Result<String> sendEmail(String to, String cc, String subject, String content, @RequestParam(required = false) MultipartFile[] files) {
        if (!StringUtil.isNullOrBlank(to) && !StringUtil.isNullOrBlank(subject) && !StringUtil.isNullOrBlank(content)) {
            try {
                if (files != null) {
                    eMailUtil.sendEmail(to, cc, subject, content, files);
                } else {
                    eMailUtil.sendEmail(to, cc, subject, content);
                }
                return Result.success(StatusCode.SUCCESS, "邮件发送成功");
            } catch (Exception e) {
                return Result.fail(StatusCode.FAIL, e.getMessage());
            }
        } else {
            return Result.fail(StatusCode.FAIL, "请求参数不完整");
        }
    }
}
