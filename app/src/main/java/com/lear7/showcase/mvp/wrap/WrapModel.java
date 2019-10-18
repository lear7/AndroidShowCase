package com.lear7.showcase.mvp.wrap;

import com.lear7.showcase.mvp.base.BaseCallBack;

public class WrapModel {

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
