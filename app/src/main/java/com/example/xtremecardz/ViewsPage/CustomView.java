package com.example.xtremecardz.ViewsPage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.xtremecardz.R;

public class CustomView extends View {
    private static final int SQUARE_SIZE = 200;
    private Rect mRectSquare;
    private Paint mPaintSquare;
    private int mSquareColor;
    private int mSquareSize;
    private Paint mPaintCircle;

    public CustomView(Context context) {
        super(context);
        init(null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set){
        mRectSquare     = new Rect();
        mPaintSquare    = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaintCircle    = new Paint();
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setColor(Color.parseColor("#00ffcc"));
        mPaintSquare.setColor(Color.WHITE);
        if(set == null){
            return;
        }
        TypedArray typedArray   = getContext().obtainStyledAttributes(set, R.styleable.CustomView);
        mSquareColor            = typedArray.getColor(R.styleable.CustomView_square_color, Color.WHITE);
        mSquareSize             = typedArray.getDimensionPixelSize(R.styleable.CustomView_square_size, SQUARE_SIZE);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mRectSquare.left   = 50;
        mRectSquare.top    = 50;
        mRectSquare.right  = mRectSquare.left + SQUARE_SIZE;
        mRectSquare.bottom = mRectSquare.top + SQUARE_SIZE;
        canvas.drawRect(mRectSquare, mPaintSquare);

        float cx, cy;
        float radius    = 100f;
        cx  = getWidth() - radius - 50f;
        cy  = mRectSquare.top + (mRectSquare.height() / 2);

        canvas.drawCircle(cx, cy, radius, mPaintCircle);
    }
}
