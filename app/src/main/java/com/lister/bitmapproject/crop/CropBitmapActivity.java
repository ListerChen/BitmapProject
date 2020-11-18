
package com.lister.bitmapproject.crop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lister.bitmapproject.R;

public class CropBitmapActivity extends AppCompatActivity implements View.OnClickListener {

    private CropBitmapLayout mCropBitmapLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_bitmap);

        mCropBitmapLayout = findViewById(R.id.crop_bitmap_layout);

        findViewById(R.id.btn_ok).setOnClickListener(this);
        mCropBitmapLayout.post(new Runnable() {
            @Override
            public void run() {
                setBitmap();
            }
        });
    }

    private void setBitmap() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test_bitmap);
        mCropBitmapLayout.setBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                if (mCropBitmapLayout.hasBitmapInit()) {
                    boolean result = mCropBitmapLayout.cropBitmap();
                    if (result) {
                        Intent intent = getIntent();
                        intent.putExtra("icon", "/sdcard/aaa/bbb/icon.png");
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Crop Bitmap Error", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}