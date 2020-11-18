
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

public class ComposeView extends View {

    private Paint mPaint;
    private PorterDuffXfermode mMode;
    private int mWidth, mHeight;
    private Bitmap mComposeBitmap;
    private Bitmap mBitmap;
    private Rect mRect;

    public ComposeView(Context context) {
        this(context, null);
    }

    public ComposeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ComposeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMode = new PorterDuffXfermode(PorterDuff.Mode.OVERLAY);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != mWidth || h != mHeight) {
            mWidth = w;
            mHeight = h;
            mComposeBitmap = BitmapUtils.decodeSampledBitmapFromResource(
                    getResources(), R.drawable.blur2, mWidth, mHeight);
            mBitmap = BitmapUtils.decodeSampledBitmapFromResource(
                    getResources(), R.drawable.compress_test, mWidth, mHeight);
            mRect = new Rect(0, 0, mWidth, mHeight);
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int sc = canvas.saveLayer(0, 0, mWidth, mHeight, null);
        canvas.drawBitmap(mComposeBitmap, null, mRect, mPaint);
        mPaint.setXfermode(mMode);
        canvas.drawBitmap(mBitmap, null, mRect, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(sc);
    }
}
