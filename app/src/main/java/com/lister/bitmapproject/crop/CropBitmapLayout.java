
package com.lister.bitmapproject.crop;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CropBitmapLayout extends FrameLayout {

    private static final String LOG_TAG = "CropBitmapLayout";

    private ImageView mImageView;
    private CropView mCropView;
    private Bitmap mBitmap;
    /**
     * Bitmap设置到ImageView上的scale
     */
    private float mScale;
    private boolean mHasBitmapInit = false;

    public CropBitmapLayout(@NonNull Context context) {
        this(context, null);
    }

    public CropBitmapLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CropBitmapLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mImageView = new ImageView(getContext());
        mCropView = new CropView(getContext());

        LayoutParams imageLayoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mImageView, imageLayoutParams);
        LayoutParams cropLayoutParams = new LayoutParams(300, 300);
        addView(mCropView, cropLayoutParams);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
        int bitmapWidth = mBitmap.getWidth();
        int bitmapHeight = mBitmap.getHeight();
        int layoutWidth = getMeasuredWidth();
        int layoutHeight = getMeasuredHeight();
        float layoutScale = layoutWidth * 1.0f / layoutHeight;
        float bitmapScale = bitmapWidth * 1.0f / bitmapHeight;
        if (bitmapScale > layoutScale) {
            // 图片的宽高比更大, 因此宽度充满
            int newLayoutHeight = (int) (layoutWidth * 1.0f / bitmapScale);
            LayoutParams lp = (LayoutParams) mImageView.getLayoutParams();
            lp.height = newLayoutHeight;
            mImageView.setLayoutParams(lp);
            mScale = bitmapWidth * 1.0f / layoutWidth;
        } else {
            // Layout宽高比更大, 因此高度充满
            int newLayoutWidth = (int) (layoutHeight * bitmapScale);
            LayoutParams lp = (LayoutParams) mImageView.getLayoutParams();
            lp.width = newLayoutWidth;
            mImageView.setLayoutParams(lp);
            mScale = bitmapHeight * 1.0f / layoutHeight;
        }
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mImageView.setImageBitmap(mBitmap);
        mHasBitmapInit = true;
    }

    public boolean hasBitmapInit() {
        return mHasBitmapInit;
    }

    public boolean cropBitmap() {
        int left = (int) (mCropView.getLeft() * mScale);
        int top = (int) (mCropView.getTop() * mScale);
        int right = (int) (mCropView.getRight() * mScale);
        int bottom = (int) (mCropView.getBottom() * mScale);
        Bitmap b = null;
        try {
            b = Bitmap.createBitmap(mBitmap, left, top, right - left, bottom - top);
        } catch (Exception e) {
            Log.e(LOG_TAG, "cropBitmap Error: " + e);
        }
        if (b == null) {
            return false;
        }
        File dir = new File("/sdcard/aaa/bbb");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, "icon.png");
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            b.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
