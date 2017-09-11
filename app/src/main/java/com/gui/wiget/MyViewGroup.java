package com.gui.wiget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by guizhigang on 17/1/6.
 */
public class MyViewGroup extends ViewGroup {
    private String TAG = "MyViewGroup";
    private int deviceWidth;

    public MyViewGroup(Context context) {
        super(context);
        init(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
        super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
    }

    private void init(Context context) {
        final Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point deviceDisplay = new Point();
        display.getSize(deviceDisplay);
        deviceWidth = deviceDisplay.x;
    }

    //EXACTLY:1073741824 AT_MOST:-214748...
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int childState = 0;
        int count = getChildCount();
        for (int i = 0; i < count; ++i) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            childState = combineMeasuredStates(childState, child.getMeasuredState());
        }

        // Report our final dimensions.
        setMeasuredDimension(resolveSizeAndState(deviceWidth, widthMeasureSpec, childState),
                resolveSizeAndState(deviceWidth, heightMeasureSpec, childState << MEASURED_HEIGHT_STATE_SHIFT));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onFinishInflate");
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int left = getPaddingLeft();
        int right = width - getPaddingRight();
        int top = getPaddingTop();
        int bottom = height - getPaddingBottom();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; ++i) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            LayoutParams layoutParams = child.getLayoutParams();
            if (child.getVisibility() != GONE) {
                setChildBounds(child, left, top, left + childWidth, top + childHeight);
                top += childHeight;
            }
        }
    }

    private void setChildBounds(View child, int l, int r, int t, int b) {
        child.layout(l, t, r, b);
    }

    /**
     * inflate xml后的回调
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinishInflate");
    }
}
