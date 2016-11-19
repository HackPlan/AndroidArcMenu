package com.hackplan.androidarcmenu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Dacer on 19/11/2016.
 */

public class SimpleCirView extends View {

    private Paint bgPaint, textPaint;
    private int radiusInPx;
    private String textStr;

    private final static int DEFAULT_BG_COLOR = Color.parseColor("#03A9F4");
    private final static int DEFAULT_TEXT_COLOR = Color.WHITE;
    private final static int DEFAULT_TEXT_SIZE_IN_SP = 22;

    public SimpleCirView(Context context) {
        this(context, null);
    }

    public SimpleCirView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleCirView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public SimpleCirView setText(String text) {
        textStr = text;
        postInvalidate();
        return this;
    }

    public SimpleCirView setTextColor(@ColorInt int color) {
        textPaint.setColor(color);
        postInvalidate();
        return this;
    }

    public SimpleCirView setTextSizeInSp(int sizeInSp) {
        textPaint.setTextSize(spToPx(sizeInSp));
        postInvalidate();
        return this;
    }

    public SimpleCirView setTextSizeInPx(int sizeInPx) {
        textPaint.setTextSize(sizeInPx);
        postInvalidate();
        return this;
    }

    public SimpleCirView setBackgroundRadiusInPx(int radiusInPx) {
        this.radiusInPx = radiusInPx;
        postInvalidate();
        return this;
    }

    public SimpleCirView setCirColor(@ColorInt int color) {
        bgPaint.setColor(color);
        postInvalidate();
        return this;
    }

    private void init(Context context) {
        radiusInPx = context.getResources().getDimensionPixelSize(R.dimen.default_simple_cir_view_radius);
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        bgPaint.setColor(DEFAULT_BG_COLOR);
        textPaint.setColor(DEFAULT_TEXT_COLOR);
        textPaint.setTextSize(spToPx(DEFAULT_TEXT_SIZE_IN_SP));
        textPaint.setTextAlign(Paint.Align.CENTER);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(getWidth()/2, getHeight()/2, radiusInPx, bgPaint);
        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2)) ;
        canvas.drawText(textStr, xPos, yPos, textPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(radiusInPx * 2, radiusInPx * 2);
    }

    private int spToPx(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getContext().getResources().getDisplayMetrics());
    }
}
