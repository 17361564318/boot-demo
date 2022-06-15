package com.feng.controller;

import com.feng.response.Result;
import com.feng.response.StatusCode;
import com.feng.service.AsyncService;
import com.feng.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author fhn
 * @create 2021/7/12
 * @software idea
 */
@RestController
@RequestMapping(value = "/demo")
public class AsyncController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/async")
    public Result<Object> async(String msg) {
        if (StringUtil.isNullOrBlank(msg)) {
            return Result.fail(StatusCode.FAIL);
        } else {
            asyncService.executorAsyncOne();
            asyncService.executorAsyncTwo();
            List<String> result = Collections.singletonList("调用成功");
            return Result.success(StatusCode.SUCCESS, result);
        }
    }
}
