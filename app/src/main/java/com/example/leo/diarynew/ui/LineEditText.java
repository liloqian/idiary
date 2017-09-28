package com.example.leo.diarynew.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by leo on 2017/8/19.
 */

public class LineEditText extends android.support.v7.widget.AppCompatEditText{

    public LineEditText(Context context) {
        super(context);
    }

    public LineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onDraw(Canvas canvas) {

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.LTGRAY);
        PathEffect effect = new DashPathEffect(new float[]{5,5,5,5,5},5);
        paint.setPathEffect(effect);

        int right = getRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int height = getHeight();
        int lineHeight = getLineHeight();
        int spcingHeight = (int) getLineSpacingExtra();
        int count = (height - paddingBottom - paddingTop ) / lineHeight;
        for (int i = 0 ; i < count ; i++){
            int baseLine = lineHeight * (i + 1) + paddingTop - spcingHeight/2;
            canvas.drawLine(paddingLeft , baseLine , (float) (right-paddingRight*1.8), baseLine , paint);
        }

        super.onDraw(canvas);
    }
}
