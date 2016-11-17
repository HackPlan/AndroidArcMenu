package com.hackplan.androidarcmenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ogaclejapan.
 * https://github.com/ogaclejapan/ArcLayout
 */

class AnimatorUtils {

    private static final String ROTATION = "rotation";
    private static final String ALPHA = "alpha";
    private static final String SCALE_X = "scaleX";
    private static final String SCALE_Y = "scaleY";
    private static final String TRANSLATION_X = "translationX";
    private static final String TRANSLATION_Y = "translationY";

    private static final int ANIM_DURATION = 400;

    private AnimatorUtils() {
        //No instances.
    }


    static void focusChild(ViewGroup viewGroup, int index) {
        if (viewGroup == null || index >= viewGroup.getChildCount()) return;

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                viewGroup.getChildAt(index),
                scaleX(1f, 1.5f),
                scaleY(1f, 1.5f)
        );

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(ANIM_DURATION);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(anim);
        animSet.start();
    }

    static void clearFocusChild(ViewGroup viewGroup, int index) {
        if (viewGroup == null || index >= viewGroup.getChildCount()) return;

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                viewGroup.getChildAt(index),
                scaleX(1.5f, 1f),
                scaleY(1.5f, 1f)
        );

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(ANIM_DURATION);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(anim);
        animSet.start();
    }

    static void showMenu(ViewGroup viewGroup, Point touchPoint, AnimatorListenerAdapter listener) {
        List<Animator> animList = new ArrayList<>();

        for (int i = 0, len = viewGroup.getChildCount(); i < len; i++) {
            animList.add(createShowItemAnimator(viewGroup.getChildAt(i), touchPoint));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(ANIM_DURATION);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(animList);
        animSet.addListener(listener);
        animSet.start();
    }

    static void hideMenu(ViewGroup viewGroup, Point touchPoint) {
        List<Animator> animList = new ArrayList<>();

        for (int i = viewGroup.getChildCount() - 1; i >= 0; i--) {
            animList.add(createHideItemAnimator(viewGroup.getChildAt(i), touchPoint));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(ANIM_DURATION);
        animSet.setInterpolator(new AnticipateInterpolator());
        animSet.playTogether(animList);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        animSet.start();
    }
    static void openMenu(ViewGroup viewGroup, int openIndex, AnimatorListenerAdapter endListener) {
        List<Animator> animList = new ArrayList<>();

        for (int i = viewGroup.getChildCount() - 1; i >= 0; i--) {
            if (openIndex == i) {
                animList.add(createOpenItemAnimator(viewGroup.getChildAt(i)));
            } else {
                animList.add(createStayHideItemAnimator(viewGroup.getChildAt(i)));
            }
        }
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(ANIM_DURATION);
        animSet.setInterpolator(new AnticipateInterpolator());
        animSet.playTogether(animList);
        animSet.addListener(endListener);
        animSet.start();
    }


    private static PropertyValuesHolder rotation(float... values) {
        return PropertyValuesHolder.ofFloat(ROTATION, values);
    }

    private static PropertyValuesHolder translationX(float... values) {
        return PropertyValuesHolder.ofFloat(TRANSLATION_X, values);
    }

    private static PropertyValuesHolder translationY(float... values) {
        return PropertyValuesHolder.ofFloat(TRANSLATION_Y, values);
    }


    private static PropertyValuesHolder alpha(float... values) {
        return PropertyValuesHolder.ofFloat(ALPHA, values);
    }

    private static PropertyValuesHolder scaleX(float... values) {
        return PropertyValuesHolder.ofFloat(SCALE_X, values);
    }

    private static PropertyValuesHolder scaleY(float... values) {
        return PropertyValuesHolder.ofFloat(SCALE_Y, values);
    }

    private static Animator createShowItemAnimator(View item, Point touchPoint) {
        float dx = touchPoint.x - item.getX();
        float dy = touchPoint.y - item.getY();
        item.setRotation(0f);
        item.setAlpha(0);
        item.setScaleX(1f);
        item.setScaleY(1f);
        item.setTranslationX(dx);
        item.setTranslationY(dy);

        return ObjectAnimator.ofPropertyValuesHolder(
                item,
                rotation(0f, 720f),
                translationX(dx, 0f),
                translationY(dy, 0f),
                alpha(0, 1f)
        );
    }
    private static Animator createOpenItemAnimator(View item) {
        return ObjectAnimator.ofPropertyValuesHolder(
                item,
                scaleX(1.5f, 4f),
                scaleY(1.5f, 4f),
                alpha(1f, 0)
        );
    }

    private static Animator createHideItemAnimator(final View item, Point touchPoint) {
        float dx = touchPoint.x - item.getX();
        float dy = touchPoint.y - item.getY();
        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                rotation(720f, 0f),
                translationX(0f, dx),
                translationY(0f, dy),
                alpha(1f, 0)
        );
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                item.setTranslationX(0f);
                item.setTranslationY(0f);
            }
        });
        return anim;
    }

    private static Animator createStayHideItemAnimator(final View item) {
        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                rotation(720f, 0f),
                scaleX(1f, 0f),
                scaleY(1f, 0f),
                alpha(1f, 0)
        );
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                item.setTranslationX(0f);
                item.setTranslationY(0f);
            }
        });
        return anim;
    }
}
