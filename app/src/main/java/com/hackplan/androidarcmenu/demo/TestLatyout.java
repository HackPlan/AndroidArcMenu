package com.hackplan.androidarcmenu.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import timber.log.Timber;

/**
 * Created by Dacer on 13/11/2016.
 */

public class TestLatyout extends RelativeLayout {
    private boolean shouldIntercept = false;

    public TestLatyout(Context context) {
        super(context);
    }

    public TestLatyout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestLatyout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Timber.e("getch %s", getChildAt(1).getMeasuredWidth());
        getChildAt(1).setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Timber.e("LLL");
                shouldIntercept = true;
                return false;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Timber.e("dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Timber.e("onInterceptTouchEvent");
        if (shouldIntercept) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Timber.e("onTouchEvent");
        return super.onTouchEvent(event);
    }
}
