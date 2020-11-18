
package com.lister.bitmapproject.joint;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.widget.ImageView;

import com.lister.bitmapproject.R;

public class JointBitmapActivity extends AppCompatActivity {

    private ImageView mTestImage1;
    private ImageView mTestImage2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joint_bitmap);

        mTestImage1 = findViewById(R.id.test_image1);
        mTestImage2 = findViewById(R.id.test_image2);

        testJointBitmap();
    }

    /**
     * 测试 Bitmap 裁剪
     */
    private void testCropBitmap() {
        Bitmap originBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test_bitmap);
        mTestImage1.setImageBitmap(originBitmap);
        mTestImage2.setImageBitmap(getCropBitmap(originBitmap));
    }

    private void testJointBitmap() {
        Bitmap originBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test_bitmap);
        mTestImage1.setImageBitmap(originBitmap);
        Bitmap splitBitmap = getCropBitmap(originBitmap);
        mTestImage2.setImageBitmap(getJointBitmap(splitBitmap));
    }

    /**
     * 将多个 Bitmap 拼接为一个
     */
    private Bitmap getJointBitmap(Bitmap splitBitmap) {
        int width = splitBitmap.getWidth();
        int height = splitBitmap.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width * 2, height * 2, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(splitBitmap, 0, 0, null);
        canvas.drawBitmap(splitBitmap, width, 0, null);
        canvas.drawBitmap(splitBitmap, 0, height, null);
        canvas.drawBitmap(splitBitmap, width, height, null);
        return bitmap;
    }

    /**
     * 将 Bitmap 裁剪为正方形, 并返回一个新的 Bitmap
     */
    private Bitmap getCropBitmap(Bitmap originBitmap) {
        // 计算 Bitmap 的宽高并裁剪
        int originWidth = originBitmap.getWidth();
        int originHeight = originBitmap.getHeight();
        int length = Math.min(originWidth, originHeight);
        return Bitmap.createBitmap(originBitmap, 0, 0, length, length);
    }
}