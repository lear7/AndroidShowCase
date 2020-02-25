package com.lear7.showcase.ui.widget;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

/**
 * @author Lear
 * @description
 * @date 2019/12/4 15:53
 */
public class SecButton extends AppCompatButton {
    public SecButton(Context context) {
        super(context);
    }

    public SecButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SecButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean performAccessibilityAction(int action, Bundle arguement) {
        // 移除所有Accessiblity的点击，防止电子劫持
        return true;
    }
}
