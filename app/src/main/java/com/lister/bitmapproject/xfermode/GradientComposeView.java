
package com.lister.bitmapproject.xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.lister.bitmapproject.BitmapUtils;
import com.lister.bitmapproject.R;

public class GradientComposeView extends View {

    private Paint mPaint;
    private Bitmap mBitmap;
    private Drawable drawable;
    private Rect mRect;
    private int mWidth, mHeight;
    private PorterDuffXfermode mMode;

    public GradientComposeView(Context context) {
        this(context, null);
    }

    public GradientComposeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientComposeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMode = new PorterDuffXfermode(PorterDuff.Mode.XOR);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mWidth != w || mHeight != h) {
            mWidth = w;
            mHeight = h;
            mBitmap = BitmapUtils.decodeSampledBitmapFromResource(
                    getResources(), R.drawable.compress_test, mWidth, mHeight);
            drawable = getResources().getDrawable(R.drawable.gradient);
            drawable.setBounds(0, 0, mWidth, mHeight);
            mRect = new Rect(0, 0, mWidth, mHeight);
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int sc = canvas.saveLayer(0, 0, mWidth, mHeight, null);
        drawable.draw(canvas);
        mPaint.setXfermode(mMode);
        canvas.drawBitmap(mBitmap, null, mRect, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(sc);
    }
}
