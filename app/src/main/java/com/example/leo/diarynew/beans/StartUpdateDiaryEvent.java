package com.example.leo.diarynew.beans;

/**
 * Created by leo on 2017/8/19.
 */

public class StartUpdateDiaryEvent {
    private int position;

    public StartUpdateDiaryEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
