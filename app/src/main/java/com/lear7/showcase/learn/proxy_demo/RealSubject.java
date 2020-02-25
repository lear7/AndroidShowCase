package com.lear7.showcase.learn.proxy_demo;

public class RealSubject implements Subject {

    @Override
    public void lawsuit() {
        System.out.println("打官司");
    }
}
