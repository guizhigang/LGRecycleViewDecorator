package com.gui.materialdesign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PercentActivity extends AppCompatActivity {

    @BindView(R.id.color_light)
    TextView colorLight;

    @BindView(R.id.text_show)
    TextView textShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percent);
        ButterKnife.bind(this);

        LinearLayout.LayoutParams layoutParamColorLight = (LinearLayout.LayoutParams) colorLight.getLayoutParams();
        LinearLayout.LayoutParams layoutParamTextShow = (LinearLayout.LayoutParams) textShow.getLayoutParams();
        layoutParamColorLight.weight = 0.7f;
        layoutParamTextShow.weight = 0.3f;


    }
}
