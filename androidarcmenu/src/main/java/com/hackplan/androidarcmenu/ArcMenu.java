package com.hackplan.androidarcmenu;

import android.app.Activity;
import android.graphics.Rect;
import android.support.annotation.DrawableRes;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Dacer on 12/11/2016.
 */

public class ArcMenu {

    public interface OnClickBtnListener{
        void onClickArcMenu(View menuView, int viewId);
    }

    private ArcMenuInterceptLayout arcLayout;
    private ArrayList<ArcButton.Builder> btnList;
    private boolean hideOnTouchUp;

    private ArcMenu(ArcMenuInterceptLayout arcLayout, ArrayList<ArcButton.Builder> btnList,
                    OnClickBtnListener listener, boolean hideOnTouchUp) {
        this.arcLayout = arcLayout;
        this.btnList = btnList;
        this.hideOnTouchUp = hideOnTouchUp;
        arcLayout.setOnClickBtnListener(listener);
    }

    public void showOn(View view) {
        if (view == null) return;
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        arcLayout.show(rect.centerX(), rect.centerY(), btnList, hideOnTouchUp);
    }

    public static class Builder implements View.OnTouchListener, View.OnLongClickListener{
        private ArrayList<ArcButton.Builder> btnList = new ArrayList<>();
        private OnClickBtnListener onClickBtnListener;
        private Activity activity;
        private ArcMenuInterceptLayout arcMenuLayout;
        private HashSet<View> onTouchViews = new HashSet<>();
        private boolean hideOnTouchUp = true;


        public Builder(Activity activity) {
            this.activity = activity;
        }

        public ArcMenu build() {
            arcMenuLayout = attachToActivity(activity);
            return new ArcMenu(arcMenuLayout, btnList, onClickBtnListener, hideOnTouchUp);
        }

        public Builder setListener(OnClickBtnListener listener) {
            this.onClickBtnListener = listener;
            return this;
        }

        public Builder addBtn(@DrawableRes int resId, int id) {
            btnList.add(new ArcButton.Builder(resId, id));
            return this;
        }

        public Builder addBtns(ArcButton.Builder... arcButtons) {
            btnList.addAll(Arrays.asList(arcButtons));
            return this;
        }

        public Builder showOnTouch(View view) {
            onTouchViews.add(view);
            view.setOnTouchListener(this);
            return this;
        }

        public Builder showOnLongClick(View view) {
            view.setOnTouchListener(this);
            view.setOnLongClickListener(this);
            return this;
        }

        public Builder hideOnTouchUp(boolean h) {
            hideOnTouchUp = h;
            return this;
        }

        @Override
        public boolean onLongClick(View v) {
            arcMenuLayout.show(lastTouchX, lastTouchY, btnList, hideOnTouchUp);
            return true;
        }

        private int lastTouchX, lastTouchY;
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN){
                if (onTouchViews.contains(v)) {
                    arcMenuLayout.show((int) event.getRawX(), (int) event.getRawY(),
                            btnList, hideOnTouchUp);
                }else {
                    //Used in onLongClick(View v)
                    lastTouchX = (int) event.getRawX();
                    lastTouchY = (int) event.getRawY();
                }
            }
            return false;
        }

        ArcMenuInterceptLayout attachToActivity(Activity activity) {
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();

            for (int i = 0; i < decorView.getChildCount(); i++) {
                View view = decorView.getChildAt(i);
                if (view != null && view instanceof ArcMenuInterceptLayout) {
                    return (ArcMenuInterceptLayout) view;
                }
            }

            ArcMenuInterceptLayout arcMenuInterceptLayout = new ArcMenuInterceptLayout(activity);
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            arcMenuInterceptLayout.setLayoutParams(params);

            for (int i = 0; i < decorView.getChildCount(); i++) {
                View v = decorView.getChildAt(i);
                decorView.removeView(v);
                arcMenuInterceptLayout.addView(v);
            }
            decorView.addView(arcMenuInterceptLayout, 0);
            return arcMenuInterceptLayout;
        }

    }
}
