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
        @DrawableRes private int resId;
        private int id;

        public Builder(@DrawableRes int resId, int id) {
            this.resId = resId;
            this.id = id;
        }

        public View getButton(Context context) {
            ImageView arcButton = new ImageView(context);
            arcButton.setImageResource(resId);
            arcButton.setTag(id);
            return arcButton;
        }
    }
}
