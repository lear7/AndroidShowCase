package com.lear7.showcase.proxy_demo;

public class ProxyLawyer implements Subject {
    private Subject subject;

    public ProxyLawyer(Subject subject) {
        this.subject = subject;
    }

    @Override
    public void lawsuit() {
        before();
        subject.lawsuit();
        after();
    }

    private void before() {
        System.out.println("签合同");
    }

    private void after() {
        System.out.println("付佣金");
    }
}
