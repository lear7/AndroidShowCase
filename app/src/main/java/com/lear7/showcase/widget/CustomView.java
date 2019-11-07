package com.lear7.showcase.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import androidx.annotation.Nullable;

import com.lear7.showcase.R;

public class CustomView extends View {

    private int lastX;
    private int lastY;

    public enum LayoutType {ByLayout, ByOffset, ByLp}

    LayoutType layoutType;

    private String hint;

    private Scroller mScroller;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomView);
        layoutType = LayoutType.values()[typedArray.getInt(R.styleable.CustomView_LayoutType, 0)];
        hint = typedArray.getString(R.styleable.CustomView_Hint);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    public void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int scrollY = getScrollY();

        int deltaX = destX - scrollX;
        int deltaY = destY - scrollY;

        mScroller.startScroll(scrollX, 0, deltaX, 0, 2000);
        invalidate();
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

                switch (layoutType) {
                    case ByLayout:
                        // 第一种重新布局（左上右下）
                        layout(getLeft() + offsetX, getTop() + offsetY,
                                getRight() + offsetX, getBottom() + offsetY);
                        break;
                    case ByOffset:
                        // 或者用第二种方法
                        offsetLeftAndRight(offsetX);
                        offsetTopAndBottom(offsetY);
                        break;
                    case ByLp:
                    default:
                        // 或者用第三种方法，不好用！！
                        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) getLayoutParams();
                        lp.leftMargin = getLeft() + offsetX;
                        lp.topMargin = getTop() + offsetY;
                        setLayoutParams(lp);
                        break;
                }
                break;
        }

        return true;
    }
}
