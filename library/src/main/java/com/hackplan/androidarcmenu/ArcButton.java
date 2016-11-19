package com.hackplan.androidarcmenu;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
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
        private View customView;

        Builder(@DrawableRes int resId, int id) {
            this.resId = resId;
            this.id = id;
        }

        public Builder(View customView, int id) {
            this.customView = customView;
            this.id = id;
        }

        View getButton(Context context) {
            View btnView = null;
            if (customView != null) {
                btnView = customView;

            } else if (resId != -1) {
                ImageView arcButton = new ImageView(context);
                arcButton.setImageResource(resId);
                btnView = arcButton;
            }

            if (btnView == null) {
                throw new RuntimeException("ArcButton does not init correctly");
            } else {
                btnView.setTag(id);
                return btnView;
            }
        }
    }
}
