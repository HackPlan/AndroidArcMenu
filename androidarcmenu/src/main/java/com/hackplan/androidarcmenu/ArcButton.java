package com.hackplan.androidarcmenu;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Dacer on 16/11/2016.
 */

public class ArcButton extends View{

    public ArcButton(Context context) {
        this(context, null);
    }

    public ArcButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public static class Builder {
        @DrawableRes private int resId = -1;
        private int id;
        private int backgroundColor = -1;
        private String text;
        private View customView;

        public Builder(@DrawableRes int resId, int id) {
            this.resId = resId;
            this.id = id;
        }

        /**
         * NOT COMPLETED
         * @param text
         * @param backgroundColor
         * @param id
         */
        private Builder(String text, int backgroundColor, int id) {
            this.text = text;
            this.backgroundColor = backgroundColor;
            this.id = id;
        }

        public Builder(View customView, int id) {
            this.customView = customView;
            this.id = id;
        }

        View getButton(Context context) {
            if (customView != null) {
                customView.setTag(id);
                return customView;

            } else if (resId != -1) {
                ImageView arcButton = new ImageView(context);
                arcButton.setImageResource(resId);
                arcButton.setTag(id);
                return arcButton;
            }
            throw new RuntimeException("ArcButton does not init correctly");
        }
    }
}
