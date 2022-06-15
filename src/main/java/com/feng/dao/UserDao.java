package com.feng.dao;

import com.feng.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author fhn
 * @create 2021/7/13
 * @software idea
 */
public interface UserDao {

    void saveUser(final User user);

    void batchSaveUser(@Param("userList") List<User> userList);

    List<Map<String, Object>> findUserByName(@Param("name") String name);

    int updateUser(final User user);

    List<Map<String, Object>> findAllUsers();

    List<User> listUsersByName(@Param("names") final String[] names);

    List<User> findUserAndAddress(@Param("user") User user);
}

