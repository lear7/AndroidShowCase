package com.lear7.showcase.proxy_demo;

import java.lang.reflect.Proxy;

public class DynamicLawyerDemo {
    public static void main(String[] args) {
        System.out.println("----------动态代理----------");

        // 真实的对象
        RealCivilSubject realSubject = new RealCivilSubject();

        // 动态创建一个民事诉讼代理的对象，该代理实现了CivilSubject接口
        CivilSubject civilProxy = (CivilSubject) Proxy.newProxyInstance(
                realSubject.getClass().getClassLoader(),
                new Class[]{CivilSubject.class},
                new DynamicProxyLawyer(realSubject)
        );

        // 通过代理调用
        civilProxy.civilLawsuit();
    }

}
