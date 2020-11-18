
package com.lister.bitmapproject.crop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lister.bitmapproject.R;

public class ChangeIconActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST_CODE = 12306;

    private ImageView mIconImageView;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE && data != null) {
                String filePath = data.getStringExtra("icon");
                setIcon(filePath);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_icon);

        mIconImageView = findViewById(R.id.image_icon);

        findViewById(R.id.btn_change_icon).setOnClickListener(this);
    }

    private void setIcon(String filePath) {
        Bitmap b = BitmapFactory.decodeFile(filePath);
        if (b != null) {
            mIconImageView.setImageBitmap(b);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change_icon:
                Intent intent = new Intent(this, CropBitmapActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }
}