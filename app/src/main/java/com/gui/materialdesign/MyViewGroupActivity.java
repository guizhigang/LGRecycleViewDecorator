package com.gui.materialdesign;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Trace;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gui.wiget.LGTagLayout;

public class MyViewGroupActivity extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view_group);

        Trace.beginSection("MyViewGroupActivity");
        final LGTagLayout tagLayout = (LGTagLayout) findViewById(R.id.tagLayout);
        LayoutInflater layoutInflater = getLayoutInflater();
        String tag;
        for (int i = 0; i <= 200; i++) {
            tag = "#tag" + i;
            final View tagView = layoutInflater.inflate(R.layout.tag_layout, tagLayout, false);
            TextView tagTextView = (TextView) tagView.findViewById(R.id.tagTextView);
            tagTextView.setText(tag);
            tagLayout.addView(tagView);

            ViewGroup.LayoutParams layoutParams = tagView.getLayoutParams();
            int width;
            tagView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            width = tagView.getMeasuredWidth();
            if (layoutParams != null) {
                double ran = Math.random();
                layoutParams.width = width + (int) (ran * 100);
                tagView.setLayoutParams(layoutParams);
            }

            tagTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ViewGroupTest", "onClick");
                    tagLayout.removeView(tagView);
                }
            });
        }
        Trace.endSection();
    }
}
