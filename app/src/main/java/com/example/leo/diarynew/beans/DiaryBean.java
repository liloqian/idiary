package com.example.leo.diarynew.beans;

import org.litepal.annotation.Encrypt;
import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by leo on 2017/8/19.
 */

public class DiaryBean extends DataSupport implements Serializable{

    private String date;
    private String title;
    private String tag;

    @Encrypt(algorithm = AES)
    private String content;

    public DiaryBean() {
    }

    public DiaryBean(String date, String title, String content, String tag) {
        this.date = date;
        this.title = title;
        this.content = content;
        this.tag = tag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "DiaryBean{" +
                "date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
