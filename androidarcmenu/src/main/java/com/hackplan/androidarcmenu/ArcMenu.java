package com.hackplan.androidarcmenu;

import android.app.Activity;
import android.graphics.Rect;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
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

    public interface OnClickMenuListener {
        void onClickArcMenu(ArcMenu arcMenu, int clickedMenuId);
    }

    private Builder builder;

    private ArcMenu(Builder builder) {
        this.builder = builder;
    }

    public void showOn(View view) {
        if (view == null) return;
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        builder.arcMenuLayout.show(this, rect.centerX(),
                rect.centerY(),
                builder.btnList,
                builder.hideOnTouchUp);
    }

    public int getId() {
        return builder.id;
    }

    public static class Builder{
        private int id = -1;
        private ArcMenu arcMenu;
        private ArrayList<ArcButton.Builder> btnList = new ArrayList<>();
        private OnClickMenuListener onClickMenuListener;
        private Activity activity;
        private ArcMenuInterceptLayout arcMenuLayout;
        private HashSet<View> onTouchViews = new HashSet<>();
        private boolean hideOnTouchUp = true;


        public Builder(Activity activity) {
            this.activity = activity;
        }

        public ArcMenu build() {
            if (arcMenu != null) throw new RuntimeException("ArcMenu.Build already built");
            arcMenuLayout = attachToActivity(activity);
            arcMenuLayout.setOnClickBtnListener(onClickMenuListener);
            arcMenu = new ArcMenu(this);
            return arcMenu;
        }

        public Builder setId(int id){
            this.id = id;
            return this;
        }

        public Builder setListener(OnClickMenuListener listener) {
            this.onClickMenuListener = listener;
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
            view.setOnTouchListener(touchListener);
            return this;
        }

        public Builder showOnLongClick(View view) {
            view.setOnTouchListener(touchListener);
            view.setOnLongClickListener(longClickListener);
            return this;
        }

        public Builder hideOnTouchUp(boolean h) {
            hideOnTouchUp = h;
            return this;
        }


        private View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                arcMenuLayout.show(arcMenu, lastTouchX, lastTouchY, btnList, hideOnTouchUp);
                return true;
            }
        };

        private int lastTouchX, lastTouchY;
        private View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    if (onTouchViews.contains(v)) {
                        arcMenuLayout.show(arcMenu,
                                (int) event.getRawX(),
                                (int) event.getRawY(),
                                btnList, hideOnTouchUp);
                    }else {
                        //Used in onLongClick(View v)
                        lastTouchX = (int) event.getRawX();
                        lastTouchY = (int) event.getRawY();
                    }
                }
                return false;
            }
        };

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
