
package com.lister.bitmapproject.xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.lister.bitmapproject.BitmapUtils;
import com.lister.bitmapproject.R;

public class ColorComposeView extends View {

    private Paint mPaint;
    private Bitmap mBitmap;
    private Rect mRect;
    private int mWidth, mHeight;
    private PorterDuffXfermode mMode;

    public ColorComposeView(Context context) {
        this(context, null);
    }

    public ColorComposeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorComposeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMode = new PorterDuffXfermode(PorterDuff.Mode.SCREEN);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mWidth != w || mHeight != h) {
            mWidth = w;
            mHeight = h;
            mBitmap = BitmapUtils.decodeSampledBitmapFromResource(
                    getResources(), R.drawable.icon3, mWidth, mHeight);
            mRect = new Rect(0, 0, mWidth, mHeight);
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        int sc = canvas.saveLayer(0, 0, mWidth, mHeight, null);
        canvas.drawColor(0x44FF0000);
        mPaint.setXfermode(mMode);
        canvas.drawBitmap(mBitmap, null, mRect, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(sc);
    }
}
