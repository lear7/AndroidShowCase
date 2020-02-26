package com.lear7.showcase.component.dagger;

import javax.inject.Inject;

public class DemoUser {

    private String firstName;
    private String lastName;

    // 构造方法上添加注释
    @Inject
    public DemoUser() {
        this.firstName = "Hua";
        this.lastName = "Li";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
