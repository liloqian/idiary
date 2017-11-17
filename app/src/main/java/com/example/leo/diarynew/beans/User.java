package com.example.leo.diarynew.beans;

/**
 * @Author: qian
 * @Description
 * @Date: Created in 16:00 2017/11/2
 **/
public class User {
    private String name;
    private String password;

    public User(){}

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
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
        this.password = password;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
