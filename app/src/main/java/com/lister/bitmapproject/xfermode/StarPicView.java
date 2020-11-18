
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

public class StarPicView extends View {

    private Paint mPaint;
    private PorterDuffXfermode mMode;
    private Bitmap mBgBitmap;
    private Bitmap mBitmap;
    private int mWidth, mHeight;
    private Rect mDrawRect;

    public StarPicView(Context context) {
        this(context, null);
    }

    public StarPicView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StarPicView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mWidth != w || mHeight != h) {
            mWidth = w;
            mHeight = h;
            mBgBitmap = BitmapUtils.decodeSampledBitmapFromResource(
                    getContext().getResources(), R.drawable.star4, mWidth, mHeight);
            mBitmap = BitmapUtils.decodeSampledBitmapFromResource(
                    getContext().getResources(), R.drawable.icon3, mWidth, mHeight);
            mDrawRect = new Rect(0, 0, mWidth, mHeight);
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int sc = canvas.saveLayer(0, 0, mWidth, mHeight, null);
        canvas.drawBitmap(mBgBitmap, null, mDrawRect, mPaint);
        mPaint.setXfermode(mMode);
        canvas.drawBitmap(mBitmap, null, mDrawRect, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(sc);
    }
}
