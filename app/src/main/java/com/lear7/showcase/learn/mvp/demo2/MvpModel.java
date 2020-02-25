package com.lear7.showcase.learn.mvp.demo2;

import com.lear7.showcase.learn.mvp.demo2.base.BaseCallBack;

public class MvpModel {

    public static void getNetDada(final String params, final BaseCallBack callBack) {

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
