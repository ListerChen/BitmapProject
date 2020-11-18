
package com.lister.bitmapproject.xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class XFerModeView extends View {

    private Paint mPaint;
    private PorterDuffXfermode mPorterDuffXfermode;
    private int mWidth;
    private int mHeight;

    public XFerModeView(Context context) {
        this(context, null);
    }

    public XFerModeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XFerModeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    }

    public void setMode(PorterDuff.Mode mode) {
        mPorterDuffXfermode = new PorterDuffXfermode(mode);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mWidth != w || mHeight != h) {
            mWidth = w;
            mHeight = h;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        int sc = canvas.saveLayer(0, 0, mWidth, mHeight, null);
        // drawCompositionInFullSize(canvas);
        drawCompositionInSelfSize(canvas);
        canvas.restoreToCount(sc);
    }

    private void drawBackground(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, mWidth, mHeight, mPaint);
    }

    /**
     * 混合图像的大小与整个View相同
     */
    private void drawCompositionInFullSize(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        Bitmap dst = makeDst();
        Bitmap src = makeSrc();
        canvas.drawBitmap(dst, 0, 0, mPaint);
        mPaint.setXfermode(mPorterDuffXfermode);
        canvas.drawBitmap(src, 0, 0, mPaint);
        mPaint.setXfermode(null);
    }

    private Bitmap makeDst() {
        Bitmap bm = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        mPaint.setColor(0xFFFFCC44);
        c.drawOval(10, 10, mWidth * 3f / 4, mHeight * 3f / 4, mPaint);
        return bm;
    }

    private Bitmap makeSrc() {
        Bitmap bm = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        mPaint.setColor(0xFF66AAFF);
        c.drawRect(mWidth * 1f / 3, mHeight * 1f / 3,
                mWidth * 19f / 20, mHeight * 19f / 20, mPaint);
        return bm;
    }

    /**
     * 混合图像的大小只有可见区域大小
     */
    private void drawCompositionInSelfSize(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xFFFFCC44);
        canvas.drawOval(10, 10, mWidth * 3f / 4, mHeight * 3f / 4, mPaint);
        mPaint.setXfermode(mPorterDuffXfermode);
        mPaint.setColor(0xFF66AAFF);
        canvas.drawRect(mWidth * 1f / 3, mHeight * 1f / 3,
                mWidth * 19f / 20, mHeight * 19f / 20, mPaint);
        mPaint.setXfermode(null);
    }

}
