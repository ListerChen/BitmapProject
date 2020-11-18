
package com.lister.bitmapproject.xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.lister.bitmapproject.BitmapUtils;
import com.lister.bitmapproject.R;

public class RoundCornerView extends View {

    private Paint mPaint;
    private PorterDuffXfermode mFerMode;
    private Bitmap mBitmap;
    private Rect mBitmapRect;
    private int mWidth;
    private int mHeight;

    public RoundCornerView(Context context) {
        this(context, null);
    }

    public RoundCornerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundCornerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFerMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mWidth != w || mHeight != h) {
            mWidth = w;
            mHeight = h;
            mBitmap = BitmapUtils.decodeSampledBitmapFromResource(
                    getContext().getResources(), R.drawable.compress_test, mWidth, mHeight);
            mBitmapRect = new Rect(0, 0, mWidth, mHeight);
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int sc = canvas.saveLayer(0, 0, mWidth, mHeight, null);
        canvas.drawRoundRect(0, 0, mWidth, mHeight, 50, 50, mPaint);
        mPaint.setXfermode(mFerMode);
        canvas.drawBitmap(mBitmap, null, mBitmapRect, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(sc);
    }

}
