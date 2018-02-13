package com.hackplan.androidarcmenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.hackplan.androidarcmenu.ArcMenu.OnClickMenuListener;

import java.util.ArrayList;

/**
 * Created by Dacer on 12/11/2016.
 */

public class ArcMenuInterceptLayout extends FrameLayout {
    private ArcMenuLayout arcMenuLayout;
    private LayoutParams childViewParams;

    public ArcMenuInterceptLayout(Context context) {
        this(context, null);
    }

    public ArcMenuInterceptLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcMenuInterceptLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        arcMenuLayout = new ArcMenuLayout(getContext());
        childViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    public void show(ArcMenu arcMenu, View v, int x, int y, ArrayList<ArcButton.Builder> btnList,
                     boolean hideOnTouchUp, int radius, double degree) {
        if (indexOfChild(arcMenuLayout) == -1) {
            addView(arcMenuLayout);
        }
        arcMenuLayout.removeAllViews();
        for (ArcButton.Builder builder : btnList) {
            arcMenuLayout.addView(builder.getButton(getContext()), childViewParams);
        }
        arcMenuLayout.show(arcMenu, v, x, y, hideOnTouchUp, radius, degree);

    }

    public void setOnClickBtnListener(OnClickMenuListener onClickMenuListener) {
        arcMenuLayout.setOnClickMenuListener(onClickMenuListener);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (arcMenuLayout.isShow()) {
            return arcMenuLayout.onTouchEvent(ev);
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (arcMenuLayout.isShow()) {
            return arcMenuLayout.onTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

}
