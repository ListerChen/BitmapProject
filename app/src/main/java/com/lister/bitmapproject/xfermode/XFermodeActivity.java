
package com.lister.bitmapproject.xfermode;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lister.bitmapproject.R;

public class XFermodeActivity extends AppCompatActivity {

    private static final int SPACE = 20;
    private static final int TEXT_HEIGHT = 50;

    private LinearLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_fermode);

        // modeTest();
    }

    private void modeTest() {
        mContainer = findViewById(R.id.mode_linear_layout);
        mContainer.post(new Runnable() {
            @Override
            public void run() {
                addModeViews();
            }
        });
    }

    private void addModeViews() {
        PorterDuff.Mode[] mode = {PorterDuff.Mode.CLEAR, PorterDuff.Mode.SRC, PorterDuff.Mode.DST,
                PorterDuff.Mode.SRC_OVER, PorterDuff.Mode.DST_OVER, PorterDuff.Mode.SRC_IN,
                PorterDuff.Mode.DST_IN, PorterDuff.Mode.SRC_OUT, PorterDuff.Mode.DST_OUT,
                PorterDuff.Mode.SRC_ATOP, PorterDuff.Mode.DST_ATOP, PorterDuff.Mode.XOR,
                PorterDuff.Mode.DARKEN, PorterDuff.Mode.LIGHTEN, PorterDuff.Mode.MULTIPLY,
                PorterDuff.Mode.SCREEN, PorterDuff.Mode.ADD, PorterDuff.Mode.OVERLAY};
        String[] modeString = {"CLEAR", "SRC", "DST", "SRC_OVER", "DST_OVER", "SRC_IN",
                "DST_IN", "SRC_OUT", "DST_OUT", "SRC_ATOP", "DST_ATOP",
                "XOR", "DARKEN", "LIGHTEN", "MULTIPLY", "SCREEN", "ADD", "OVERLAY"};
        int width = mContainer.getWidth();
        int length = (width - SPACE * 5) / 4;
        for (int row = 0; row < 5; row++) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, length + TEXT_HEIGHT);
            lp.topMargin = SPACE;
            LinearLayout lineLinearLayout = new LinearLayout(this);
            lineLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            lineLinearLayout.setLayoutParams(lp);
            // 每一行绘制4个
            for (int column = 0; column < 4; column++) {
                int index = row * 4 + column;
                if (index >= 18) {
                    break;
                }
                LinearLayout.LayoutParams modeLinearLp = new LinearLayout.LayoutParams(
                        length, length + TEXT_HEIGHT);
                modeLinearLp.setMarginStart(SPACE);
                LinearLayout modeLinearLayout = new LinearLayout(this);
                modeLinearLayout.setOrientation(LinearLayout.VERTICAL);
                lineLinearLayout.addView(modeLinearLayout, modeLinearLp);
                // 绘制文字与图像混合图案
                LinearLayout.LayoutParams textLp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, TEXT_HEIGHT);
                TextView textView = new TextView(this);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(12);
                textView.setTextColor(Color.BLACK);
                textView.setText(modeString[index]);
                LinearLayout.LayoutParams viewLp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, length);
                XFerModeView view = new XFerModeView(this);
                view.setMode(mode[index]);
                modeLinearLayout.addView(textView, textLp);
                modeLinearLayout.addView(view, viewLp);
            }
            mContainer.addView(lineLinearLayout);
        }
    }
}
