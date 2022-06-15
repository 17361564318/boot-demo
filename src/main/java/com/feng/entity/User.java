package com.feng.entity;

import com.feng.utils.Md5Util;

import java.io.Serializable;
import java.util.List;

/**
 * @author fhn
 * @create 2021/7/13
 * @software idea
 */
public class User implements Serializable {
    private Long id;
    private Integer age;
    private String name;
    private String password;
    private String gender;
    private List<Address> addressList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Md5Util.stringMD5(password);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", addressList=" + addressList +
                '}';
    }
}
