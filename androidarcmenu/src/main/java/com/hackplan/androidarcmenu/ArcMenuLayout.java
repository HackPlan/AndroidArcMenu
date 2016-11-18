package com.hackplan.androidarcmenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.hackplan.androidarcmenu.ArcMenu.OnClickMenuListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dacer on 12/11/2016.
 */

public class ArcMenuLayout extends ViewGroup {
    private Point touchPoint = new Point();
    private final Rect mScreenRect = new Rect();
    private Rect tempRect = new Rect();
    private ArcMenu arcMenu;
    private OnClickMenuListener onClickMenuListener;
    private boolean show = false;
    private boolean hideOnTouchUp = true;

    public ArcMenuLayout(Context context) {
        this(context, null);
    }

    public ArcMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isShow() {
        return show;
    }

    private ArrayList<PointF> menuPoints = new ArrayList<>();
    private int radius = (int) dpToPx(80f);
    private double arcRadians = Math.toRadians(90d);

    public void show(ArcMenu arcMenu, int x, int y, boolean hideOnTouchUp) {
        if (getChildCount() <= 0) return;
        this.hideOnTouchUp = hideOnTouchUp;
        this.arcMenu = arcMenu;
        show = true;
        if (x == mScreenRect.centerX() && y == mScreenRect.centerY()) y += 1;

        touchPoint.set(x, y);
        menuPoints.clear();
        double alpha = Math.atan((double) (y - mScreenRect.centerY()) / (x - mScreenRect.centerX()));
        if (x < mScreenRect.centerX()) alpha += Math.PI;
        for (int i=0; i<getChildCount(); i++) {
            double b;
            if (getChildCount() == 1) {
                b = arcRadians / 2;
            }else {
                b = i * (1D / (getChildCount() - 1)) * arcRadians;
            }
            menuPoints.add(PointUtils.getPoint(new PointF(x, y), radius,
                    Math.PI + alpha - arcRadians / 2 + b));
        }
        requestLayout();
    }

    public void setOnClickMenuListener(OnClickMenuListener onClickMenuListener) {
        this.onClickMenuListener = onClickMenuListener;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getRectSize(mScreenRect);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!show) return;
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            child.setAlpha(0L);
            child.layout((int) (menuPoints.get(i).x - child.getMeasuredWidth() / 2),
                    (int) (menuPoints.get(i).y - child.getMeasuredHeight() / 2),
                    (int) (menuPoints.get(i).x + child.getMeasuredWidth() / 2),
                    (int) (menuPoints.get(i).y + child.getMeasuredHeight() / 2));
        }
        AnimatorUtils.showMenu(this, touchPoint, animListener);
    }

    private int lastFocusIndex = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = MotionEventCompat.getX(event, 0);
        float y = MotionEventCompat.getY(event, 0);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                hideOnTouchUp = true;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (!show) break;
                if (lastFocusIndex != -1) {
                    show = false;
                    AnimatorUtils.openMenu(this, lastFocusIndex, animListener);
                    if (onClickMenuListener != null) {
                        View clickedView = getChildAt(lastFocusIndex);
                        onClickMenuListener.onClickArcMenu(arcMenu, (int) clickedView.getTag());
                    }
                } else if (hideOnTouchUp) {
                    AnimatorUtils.hideMenu(this, touchPoint);
                    show = false;
                } else {
                    hideOnTouchUp = true;
                }
                lastFocusIndex = -1;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!animFinished) break;
                tempRect = new Rect();
                boolean isOverMenu = false;
                for (int i = 0; i < getChildCount(); i++) {
                    getChildAt(i).getGlobalVisibleRect(tempRect);
                    if (tempRect.contains((int) x, (int) y)) {
                        isOverMenu = true;
                        if (lastFocusIndex == i) break;
                        if (lastFocusIndex != -1)
                            AnimatorUtils.clearFocusChild(this, lastFocusIndex);
                        AnimatorUtils.focusChild(this, i);
                        lastFocusIndex = i;
                        break;
                    }
                }
                if (!isOverMenu && lastFocusIndex != -1) {
                    AnimatorUtils.clearFocusChild(this, lastFocusIndex);
                    lastFocusIndex = -1;
                }
                break;
        }

        return show;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = resolveSizeAndState(0, widthMeasureSpec, 1);
        int h = resolveSizeAndState(0, heightMeasureSpec, 0);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View children = getChildAt(i);
            measureChild(children, widthMeasureSpec, heightMeasureSpec);
        }
        setMeasuredDimension(w, h);
    }

    public float dpToPx(float dipValue) {
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    private boolean animFinished = true;

    private AnimatorListenerAdapter animListener = new AnimatorListenerAdapter() {

        @Override
        public void onAnimationStart(Animator animation) {
            animFinished = false;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            animFinished = true;
            if (!show) removeAllViews();
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            animFinished = true;
        }
    };

}
