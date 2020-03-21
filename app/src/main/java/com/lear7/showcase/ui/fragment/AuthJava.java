package com.lear7.showcase.ui.fragment;

import java.text.DecimalFormat;

public class AuthJava {

    public static String getNewPrice(String s) {
        DecimalFormat df = new DecimalFormat("######");
        String price = df.format(Arith.mul(Double.valueOf(s), (double) 100, 0));
        return price;
    }

    public static Long getNewPrice2(double s) {
        return Long.parseLong(((int) (s * 100)) + "");
    }
}
