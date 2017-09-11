package com.gui.wiget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by guizhigang on 17/1/6.
 */
public class LGTagLayout extends ViewGroup {
    private int deviceWidth;

    public LGTagLayout(Context context) {
        super(context);
        init(context);
    }

    public LGTagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LGTagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LGTagLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        final Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point deviceDisplay = new Point();
        display.getSize(deviceDisplay);
        deviceWidth = deviceDisplay.x;
    }

    /**
     * 计算ViewGroup的尺寸
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        // Measurement will ultimately be computing these values.
        int totalHeight = 0;
        int totalWidth = deviceWidth;
        int childState = 0;
        int mLeftWidth = 0;
        int rowHeight = 0;

        // Measure the child.
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        // Iterate through all children, measuring them and computing our dimensions
        // from their size.
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE)
                continue;
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            int lMargin = layoutParams.leftMargin;
            int rMargin = layoutParams.rightMargin;
            int tMargin = layoutParams.topMargin;
            int bMargin = layoutParams.bottomMargin;

            mLeftWidth += child.getMeasuredWidth() + lMargin + rMargin;
            int childHeight = child.getMeasuredHeight();
            if (mLeftWidth > deviceWidth) {
                totalHeight += rowHeight;
                mLeftWidth = child.getMeasuredWidth() + lMargin + rMargin;
                rowHeight = 0;
            }
            rowHeight = Math.max(rowHeight, childHeight + tMargin + bMargin);
            childState = combineMeasuredStates(childState, child.getMeasuredState());
        }

        totalHeight += rowHeight;

        // Check against our minimum height and width
        totalHeight = Math.max(totalHeight, getSuggestedMinimumHeight());
        totalWidth = Math.max(totalWidth, getSuggestedMinimumWidth());

        // Report our final dimensions.
        setMeasuredDimension(resolveSizeAndState(totalWidth, widthMeasureSpec, childState),
                resolveSizeAndState(totalHeight, heightMeasureSpec, childState << MEASURED_HEIGHT_STATE_SHIFT));
    }

    /**
     * 放置子控件在ViewGroup中的位置
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        int curWidth, curHeight, curLeft, curTop, maxHeight;

        //get the available size of child view
        final int childLeft = this.getPaddingLeft();
        final int childTop = this.getPaddingTop();
        final int childRight = this.getMeasuredWidth() - this.getPaddingRight();
        final int childBottom = this.getMeasuredHeight() - this.getPaddingBottom();
        int x = 0;
        int y = 0;

        maxHeight = 0;
        curTop = childTop;
        x = childLeft;

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE)
                return;

            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            int lMargin = layoutParams.leftMargin;
            int rMargin = layoutParams.rightMargin;
            int tMargin = layoutParams.topMargin;
            int bMargin = layoutParams.bottomMargin;

            curWidth = child.getMeasuredWidth();
            curHeight = child.getMeasuredHeight();

            //wrap is reach to the end
            if (x + curWidth + lMargin + rMargin > childRight) {
                x = childLeft;
                curTop += maxHeight;
                maxHeight = 0;
            }
            //do the layout
            child.layout(x + lMargin, curTop + tMargin, x + lMargin + curWidth, curTop + tMargin + curHeight);
            //store the max height
            if (maxHeight < curHeight + tMargin + bMargin)
                maxHeight = curHeight + tMargin + bMargin;
            x += curWidth + lMargin + rMargin;
        }
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        MarginLayoutParams layoutParams = new MarginLayoutParams(getContext(), attrs);
//        layoutParams.width = 100 + (int) (Math.random() * 50);
        return layoutParams;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return super.generateLayoutParams(p);
    }
}
