
package com.lister.bitmapproject.crop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class CropView extends View {

    private static final int RECT_PADDING = 20;
    private static final int TOUCH_PADDING = 50;

    private static final int MODE_NONE = 0;
    private static final int MODE_DRAG = 1;
    private static final int MODE_ZOOM = 2;

    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int mStrokeWidth = 7;

    private int mTouchMode = MODE_NONE;
    private int mDownX;
    private int mDownY;
    private int mLastX;
    private int mLastY;

    public CropView(Context context) {
        this(context, null);
    }

    public CropView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CropView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int curX = (int) event.getX();
        int curY = (int) event.getY();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mDownX = curX;
                mDownY = curY;
                mLastX = curX;
                mLastY = curY;
                if (mDownX >= (mWidth - TOUCH_PADDING) && mDownY >= (mHeight - TOUCH_PADDING)) {
                    mTouchMode = MODE_ZOOM;
                } else {
                    mTouchMode = MODE_DRAG;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mTouchMode == MODE_DRAG) {
                    int moveX = curX - mDownX;
                    int moveY = curY - mDownY;
                    int w = mWidth;
                    int h = mHeight;
                    int left = getLeft() + moveX;
                    int top = getTop() + moveY;
                    setLeft(left);
                    setTop(top);
                    setRight(left + w);
                    setBottom(top + h);
                } else if (mTouchMode == MODE_ZOOM) {
                    int delta = ((curX - mLastX) + (curY - mLastY)) / 2;
                    int right = getRight() + delta;
                    int bottom = getBottom() + delta;
                    setRight(right);
                    setBottom(bottom);
                    mLastX = curX;
                    mLastY = curY;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mTouchMode = MODE_NONE;
                break;
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(mStrokeWidth);
        canvas.drawRect(RECT_PADDING, RECT_PADDING,
                mWidth - RECT_PADDING, mHeight - RECT_PADDING, mPaint);
    }
}
