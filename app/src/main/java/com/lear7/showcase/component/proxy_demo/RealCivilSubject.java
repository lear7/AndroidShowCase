package com.lear7.showcase.component.proxy_demo;

public class RealCivilSubject implements CivilSubject {

    @Override
    public void civilLawsuit() {
        System.out.println("打官司");
    }
}
