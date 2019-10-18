package com.lear7.showcase.events;

public class BaseEvent {
    String data;

    public BaseEvent(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

