
package com.lister.bitmapproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.lister.bitmapproject.color.BitmapColorActivity;
import com.lister.bitmapproject.compress.CompressActivity;
import com.lister.bitmapproject.crop.ChangeIconActivity;
import com.lister.bitmapproject.joint.JointBitmapActivity;
import com.lister.bitmapproject.matrix.BitmapMatrixActivity;
import com.lister.bitmapproject.xfermode.XFermodeActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_crop_bitmap).setOnClickListener(this);
        findViewById(R.id.btn_joint_bitmap).setOnClickListener(this);
        findViewById(R.id.btn_compress_bitmap).setOnClickListener(this);
        findViewById(R.id.btn_bitmap_matrix).setOnClickListener(this);
        findViewById(R.id.btn_bitmap_color).setOnClickListener(this);
        findViewById(R.id.btn_bitmap_xfermode).setOnClickListener(this);

        requestStoragePermission();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_crop_bitmap:
                startActivity(new Intent(this, ChangeIconActivity.class));
                break;
            case R.id.btn_joint_bitmap:
                startActivity(new Intent(this, JointBitmapActivity.class));
                break;
            case R.id.btn_compress_bitmap:
                startActivity(new Intent(this, CompressActivity.class));
                break;
            case R.id.btn_bitmap_matrix:
                startActivity(new Intent(this, BitmapMatrixActivity.class));
                break;
            case R.id.btn_bitmap_color:
                startActivity(new Intent(this, BitmapColorActivity.class));
                break;
            case R.id.btn_bitmap_xfermode:
                startActivity(new Intent(this, XFermodeActivity.class));
                break;
        }
    }

    private void requestStoragePermission() {
        try {
            int permission = ActivityCompat.checkSelfPermission(
                    this, PERMISSIONS_STORAGE[0]);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this, PERMISSIONS_STORAGE, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
