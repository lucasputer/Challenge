package com.application.challenge.challenge.domain;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by lucas on 22/12/14.
 */
public class SquareView extends LinearLayout {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SquareView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public SquareView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }


    @Override public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = width > height ? height : width;
        setMeasuredDimension(size, size);
    }

}
