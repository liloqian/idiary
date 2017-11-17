package com.example.leo.diarynew.beans;

import java.util.List;

/**
 * @Author: qian
 * @Description
 * @Date: Created in 17:12 2017/11/3
 **/
public class UserInfo {
    private User user;
    private List<DiaryBean> diaryBeans;

    public UserInfo(User user, List<DiaryBean> diaryBeans) {
        this.user = user;
        this.diaryBeans = diaryBeans;
    }

    public UserInfo(){}

    @Override
    public String toString() {
        return "UserInfo{" +
                "user=" + user +
                ", diaryBeans=" + diaryBeans +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<DiaryBean> getDiaryBeans() {
        return diaryBeans;
    }

    public void setDiaryBeans(List<DiaryBean> diaryBeans) {
        this.diaryBeans = diaryBeans;
    }
}
