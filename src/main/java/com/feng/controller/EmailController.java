package com.feng.controller;


import com.feng.response.Result;
import com.feng.response.StatusCode;
import com.feng.utils.EMailUtil;
import com.feng.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EMailUtil eMailUtil;

    @PostMapping("/sendEmail")
    public Result<String> sendEmail(String to, String cc, String subject, String content) {
        if (!StringUtil.isNullOrBlank(to) && !StringUtil.isNullOrBlank(subject) && !StringUtil.isNullOrBlank(content)) {
            eMailUtil.sendEmail(to, cc, subject, content);
            return Result.success(StatusCode.SUCCESS, "邮件发送成功");
        } else {
            return Result.fail(StatusCode.FAIL, "请求参数不完整");
        }
    }
}
