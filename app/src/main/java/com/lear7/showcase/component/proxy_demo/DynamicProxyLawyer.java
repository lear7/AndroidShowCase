package com.lear7.showcase.component.proxy_demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicProxyLawyer implements InvocationHandler {
    /**
     * 代理的真实对象
     */
    private Object subject;

    public DynamicProxyLawyer(Object subject) {
        this.subject = subject;
    }

    /**
     * @param proxy
     * @param method 所代理的真实对象，某个方法的Method对象
     * @param args   所代理的真实对象，某个方法接受的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        // 重点是这里的调用
        method.invoke(subject, args);
        after();
        return null;
    }


    private void before() {
        System.out.println("签合同");
    }

    private void after() {
        System.out.println("付佣金");
    }
}
