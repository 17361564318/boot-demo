package com.feng.controller;

import com.feng.Exception.BootDemoException;
import com.feng.entity.User;
import com.feng.response.Result;
import com.feng.response.StatusCode;
import com.feng.service.UserService;
import com.feng.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author fhn
 * @create 2021/7/13
 * @software idea
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public Result addUser(@RequestBody User user) {
        try {
            userService.saveUser(user);
            return Result.success(StatusCode.SUCCESS, user);
        } catch (BootDemoException e) {
            return Result.fail(e.getStatusCode());
        }
    }

    @GetMapping
    public Result<?> findUser(String name) {
        if (StringUtil.isNullOrBlank(name)) {
            return Result.fail(StatusCode.PARAM_ERROR);
        }
        List<Map<String, Object>> userByName = userService.findUserByName(name);
        return Result.success(StatusCode.SUCCESS, userByName);
    }

    @PutMapping
    public Result updateUser(User user) {
        int i = userService.updateUser(user);
        return i == 0 ? Result.fail(StatusCode.FAIL) : Result.success(StatusCode.SUCCESS, userService.findUserAndAddress(user));
    }

    @GetMapping(value = "/export-excel-user")
    public void exportExcelUser(HttpServletResponse response) {
        userService.exportAllUserExcel(response);
    }

    @GetMapping("findUserAndAddress")
    public Result findUserAndAddress(User user) {
        if (!Objects.isNull(user)) {
            return Result.success(StatusCode.SUCCESS, userService.findUserAndAddress(user));
        } else {
            return Result.fail(StatusCode.FAIL);
        }
    }

}
