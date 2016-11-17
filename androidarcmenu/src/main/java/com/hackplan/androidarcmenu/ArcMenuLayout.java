package com.hackplan.androidarcmenu;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
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

import com.hackplan.androidarcmenu.ArcMenu.OnClickBtnListener;

/**
 * Created by Dacer on 12/11/2016.
 */

public class ArcMenuLayout extends ViewGroup implements Animator.AnimatorListener{
    private Point touchPoint = new Point();
    private final Rect mScreenRect = new Rect();
    private Rect tempRect = new Rect();
    private OnClickBtnListener onClickBtnListener;
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

    private float xFirst, yFirst, xEnd, yEnd;
    private int radius = (int)dpToPx(80f);
    private double radAlpha = Math.toRadians(90d);
    public void show(int x, int y, boolean hideOnTouchUp) {
        this.hideOnTouchUp = hideOnTouchUp;
        show = true;
        touchPoint.set(x, y);
        double radO = Math.PI - radAlpha/2 -
                Math.atan((touchPoint.x - mScreenRect.centerX()) / (touchPoint.y - mScreenRect.centerY()));
        double rad2 = Math.atan((touchPoint.x - mScreenRect.centerX()) / (touchPoint.y - mScreenRect.centerY())) - radAlpha/2;
        xFirst = touchPoint.x - ((float) (radius * Math.sin(radO)));
        yFirst = touchPoint.y + ((float) (Math.cos(radO) * radius));
        xEnd = touchPoint.x - ((float) (radius * Math.sin(rad2)));
        yEnd = touchPoint.y - ((float) (Math.cos(rad2) * radius));
        requestLayout();
    }
    
    public void setOnClickBtnListener(OnClickBtnListener onClickBtnListener) {
        this.onClickBtnListener = onClickBtnListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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
        if (!show) return ;
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            child.setAlpha(0L);
            if (i == 0) {
                child.layout((int)xFirst - child.getMeasuredWidth()/2,
                        (int)yFirst - child.getMeasuredHeight()/2,
                        (int)xFirst + child.getMeasuredWidth()/2,
                        (int)yFirst + child.getMeasuredHeight()/2);
            } else {

                child.layout((int)xEnd - child.getMeasuredWidth()/2,
                        (int)yEnd - child.getMeasuredHeight()/2,
                        (int)(xEnd + child.getMeasuredWidth()/2),
                        (int)(yEnd + child.getMeasuredHeight()/2));
            }
        }
        AnimatorUtils.showMenu(this, touchPoint, this);
    }

    private int lastFocusIndex = -1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = MotionEventCompat.getX(event, 0);
        float y = MotionEventCompat.getY(event, 0);

        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (!show) break;
                if (lastFocusIndex != -1) {
                    AnimatorUtils.openMenu(this, lastFocusIndex);
                    show = false;
                    if (onClickBtnListener != null) {
                        View clickedView = getChildAt(lastFocusIndex);
                        onClickBtnListener.onClickArcMenu(clickedView, (int)clickedView.getTag());
                    }
                } else if (hideOnTouchUp) {
                    AnimatorUtils.hideMenu(this, touchPoint);
                    show = false;
                }else {
                    hideOnTouchUp = true;
                }
                lastFocusIndex = -1;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!animFinished) break;
                tempRect = new Rect();
                boolean isOverMenu = false;
                for (int i=0; i<getChildCount(); i++) {
                    getChildAt(i).getGlobalVisibleRect(tempRect);
                    if (tempRect.contains((int)x, (int)y)) {
                        isOverMenu = true;
                        if (lastFocusIndex == i) break;
                        if (lastFocusIndex != -1) AnimatorUtils.clearFocusChild(this, lastFocusIndex);
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
        for(int i = 0 ; i < childCount ; i ++){
            View children = getChildAt(i);
            measureChild(children,widthMeasureSpec,heightMeasureSpec);
        }
        setMeasuredDimension(w, h);
    }

    public float dpToPx(float dipValue) {
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    private boolean animFinished = true;
    @Override
    public void onAnimationStart(Animator animation) {
        animFinished = false;
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        animFinished = true;
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        animFinished = true;
    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
