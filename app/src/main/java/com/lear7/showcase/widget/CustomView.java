package com.lear7.showcase.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

public class CustomView extends View {

    private int lastX;
    private int lastY;


    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX;
                int offsetY = y - lastY;

//                // 第一种重新布局（左上右下）
//                layout(getLeft() + offsetX, getTop() + offsetY,
//                        getRight() + offsetX, getBottom() + offsetY);

//                 // 或者用第二种方法
//                 offsetLeftAndRight(offsetX);
//                 offsetTopAndBottom(offsetY);

                // 或者用第三种方法
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) getLayoutParams();
                lp.leftMargin = getLeft() + offsetX;
                lp.rightMargin = getRight() + offsetY;
                setLayoutParams(lp);

                break;
        }

        return true;
    }
}
