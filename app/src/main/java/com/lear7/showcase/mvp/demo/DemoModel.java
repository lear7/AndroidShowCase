package com.lear7.showcase.mvp.demo;

public class DemoModel {

    public static void getNetDada(final String params, final DemoCallBack callBack) {

        switch (params) {
            case "normal":
                callBack.onSuccess("Net Request Succceed!");
                break;
            case "error":
                callBack.onError("Request Failed");
                break;
            case "complete":
                callBack.onComplete();
                break;
        }
    }

}
