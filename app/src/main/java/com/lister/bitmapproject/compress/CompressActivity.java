
package com.lister.bitmapproject.compress;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.lister.bitmapproject.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CompressActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int BITMAP_SCALE = 2;

    private Bitmap mOriginBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compress);

        findViewById(R.id.quality_compress_jpg).setOnClickListener(this);
        findViewById(R.id.quality_compress_png).setOnClickListener(this);
        findViewById(R.id.quality_compress_webp).setOnClickListener(this);
        findViewById(R.id.size_compress1).setOnClickListener(this);
        findViewById(R.id.size_compress2).setOnClickListener(this);

        mOriginBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.compress_test);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quality_compress_jpg:
                qualityCompressJPG();
                break;
            case R.id.quality_compress_png:
                qualityCompressPNG();
                break;
            case R.id.quality_compress_webp:
                qualityCompressWEBP();
                break;
            case R.id.size_compress1:
                sizeCompress1(BITMAP_SCALE);
                break;
            case R.id.size_compress2:
                sizeCompress2(BITMAP_SCALE);
                break;
        }
    }

    private void qualityCompressJPG() {
        OutputStream os = getOutputStreamByName("jpgFile60.jpeg");
        if (os != null) {
            mOriginBitmap.compress(Bitmap.CompressFormat.JPEG, 60, os);
        }
    }

    private void qualityCompressPNG() {
        OutputStream os = getOutputStreamByName("pngFile60.png");
        if (os != null) {
            mOriginBitmap.compress(Bitmap.CompressFormat.PNG, 60, os);
        }
    }

    private void qualityCompressWEBP() {
        OutputStream os = getOutputStreamByName("webpFile60.webp");
        if (os != null) {
            mOriginBitmap.compress(Bitmap.CompressFormat.WEBP, 60, os);
        }
    }

    private void sizeCompress1(int scale) {
        int width = mOriginBitmap.getWidth() / scale;
        int height = mOriginBitmap.getHeight() / scale;
        Bitmap b = Bitmap.createScaledBitmap(mOriginBitmap, width, height, false);
        OutputStream os = getOutputStreamByName("sizeCompress1.webp");
        if (os != null) {
            b.compress(Bitmap.CompressFormat.WEBP, 100, os);
        }
    }

    private void sizeCompress2(int scale) {
        int width = mOriginBitmap.getWidth() / scale;
        int height = mOriginBitmap.getHeight() / scale;
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(b);
        Rect rect = new Rect(0, 0, width, height);
        canvas.drawBitmap(mOriginBitmap, null, rect, null);
        OutputStream os = getOutputStreamByName("sizeCompress2_565.webp");
        if (os != null) {
            b.compress(Bitmap.CompressFormat.WEBP, 100, os);
        }
    }

    private OutputStream getOutputStreamByName(String fileName) {
        BufferedOutputStream bos = null;
        File dir = new File("/sdcard/aaa/ccc");
        boolean dirExist = true;
        if (!dir.exists()) {
            dirExist = dir.mkdirs();
        }
        if (dirExist) {
            File file = new File(dir, fileName);
            if (file.exists()) {
                file.delete();
            }
            try {
                boolean fileExist;
                fileExist = file.createNewFile();
                if (fileExist) {
                    bos = new BufferedOutputStream(new FileOutputStream(file));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bos;
    }
}