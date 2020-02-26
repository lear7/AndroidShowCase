package com.lear7.showcase.component.proxy_demo;

public class LawyerDemo {
    public static void main(String[] args) {
        System.out.println("----------静态代理----------");
        Subject subject = new RealSubject();
        ProxyLawyer proxyLawyer = new ProxyLawyer(subject);
        proxyLawyer.lawsuit();
    }

}
