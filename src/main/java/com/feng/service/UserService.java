package com.feng.service;

import com.feng.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author fhn
 * @create 2021/7/13
 * @software idea
 */
public interface UserService {
    /**
     * 新增用户
     *
     * @param user 用户信息
     * @return 受影响的行数
     */
    int saveUser(User user);

    void batchSaveUser(List<User> userList);

    /**
     * 根据用户名查找用户
     *
     * @param name 用户名
     * @return 用户信息
     */
    List<Map<String, Object>> findUserByName(String name);

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return 受影响的行数
     */
    int updateUser(User user);

    /**
     * 导出所有用户信息到excel
     *
     * @param response
     */
    void exportAllUserExcel(HttpServletResponse response);

    /**
     * 多人查询，按照名字进行模糊查询
     *
     * @param names
     * @return
     */
    List<User> findUserByName(String[] names);

    /**
     * 按照用户id查找用户详情，包括地址
     *
     * @param user user
     * @return userBean
     */
    List<User> findUserAndAddress(User user);

}
