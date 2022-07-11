package com.feng.service.impl;

import com.feng.Exception.BootDemoException;
import com.feng.annotation.SystemLog;
import com.feng.dao.AddressDao;
import com.feng.dao.UserDao;
import com.feng.entity.Address;
import com.feng.entity.User;
import com.feng.response.StatusCode;
import com.feng.service.UserService;
import com.feng.utils.ExcelUtil;
import com.feng.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author fhn
 * @create 2021/7/13
 * @software idea
 */
@Service
public class UserServiceImpl implements UserService {

    private static final ThreadLocal<DateFormat> df = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd : HH:mm:ss"));

    @Autowired
    private UserDao userDao;

    @Resource
    private AddressDao addressDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveUser(final User user) {
        if (!Objects.isNull(user)) {
            // 首先查找是否已存在相同的用户名
            List<Map<String, Object>> oldUser = findUserByName(user.getName());
            if (oldUser.isEmpty()) {
                // 保存用户
                userDao.saveUser(user);
                // 拿到刚才新增的用户的id
                Long userId = user.getId();
                // 保存完用户之后需要进行地址的保存
                List<Address> addressList = user.getAddressList();
                if (addressList != null) {
                    addressList.forEach(address -> {
                        address.setUserId(userId);
                        addressDao.saveAddress(address);
                    });
                }
                return userId.intValue();
            } else {
                throw new BootDemoException(StatusCode.USER_ALREADY_EXISTS);
            }
        }
        return 0;
    }

    @Override
    public void batchSaveUser(List<User> userList) {
        if (!userList.isEmpty()) {
            userDao.batchSaveUser(userList);
        }
    }

    @Override
    public List<Map<String, Object>> findUserByName(String name) {
        if (!StringUtil.isNullOrBlank(name)) {
            DateFormat format = df.get();
            String now = format.format(new Date());
            List<Map<String, Object>> user = userDao.findUserByName(name);
            return user.parallelStream().peek(s -> {
                s.remove("id");
                s.put("currentTime", now);
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public int updateUser(final User user) {
        if (StringUtil.isNullOrBlank(user)) {
            return 0;
        }
        return userDao.updateUser(user);
    }

    @Override
    public void exportAllUserExcel(HttpServletResponse response) {
        List<Map<String, Object>> allUsers = userDao.findAllUsers();
///       后续可能继续使用
//        allUsers = allUsers.parallelStream().map(s -> {s.remove("id");
//            return s;
//        }).collect(Collectors.toList());
        if (!allUsers.isEmpty()) {
            List<List<String>> dataList = new ArrayList<>();
            for (Map<String, Object> map : allUsers) {
                List<String> tempList = new ArrayList<>();
                Set<Map.Entry<String, Object>> set = map.entrySet();
                for (Map.Entry<String, Object> entry : set) {
                    tempList.add(entry.getValue().toString());
                }
                dataList.add(tempList);
            }
            List<String> columnList = Arrays.asList("密码", "性别", "姓名", "ID", "年龄");
            ExcelUtil.exportExcelUser(response, "user.xlsx", columnList, dataList);
        }
    }

    @Override
    public List<User> findUserByName(String[] names) {
        return userDao.listUsersByName(names);
    }

    @Override
    @SystemLog(message = "查询用户以及地址")
    public List<User> findUserAndAddress(User user) {
        return userDao.findUserAndAddress(user);
    }
}
