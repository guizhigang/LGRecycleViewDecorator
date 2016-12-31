package com.gui.materialdesign;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by guizhigang on 16/12/30.
 */
public class HorizontalDecorater extends RecyclerView.ItemDecoration {
    private Paint paint;
    private Context context;
    private int mInsetsLeft = 10;
    private int mInsetsTop = 10;
    private int mInsetsRight = 10;
    private int mInsetsBottom = 10;
    public static final int HORIZONTAL = 0X1;
    public static final int VERTICAL = 0X2;
    private int oritation = 0;

    public HorizontalDecorater(Context context) {
        this.context = context;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mInsetsLeft, mInsetsTop, mInsetsRight, mInsetsBottom);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int childCount = parent.getChildCount();
        if (childCount <= 0) return;
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            drawGridDecorater(c, parent, (GridLayoutManager) manager);
        } else if (manager instanceof LinearLayoutManager) {
            drawLinearDecorater(c,parent,(LinearLayoutManager)manager);
        }
    }

    private void drawLinearDecorater(Canvas c, RecyclerView parent, LinearLayoutManager manager) {
        int childCount = parent.getChildCount();
        int orientation = manager.getOrientation();
        int top = parent.getPaddingTop();
        int bottom = 0;
        if(orientation == LinearLayoutManager.HORIZONTAL) {
            for (int i = 0; i < childCount; ++i) {
                View child = parent.getChildAt(i);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                int mCRight = params.rightMargin;
                int mCBottom = params.bottomMargin;

                bottom = child.getBottom() + mInsetsBottom + mCBottom;
                int left = child.getRight() + mCRight + mInsetsRight;
                int right = left + 2;
                c.drawRect(left, top, right, bottom, paint);
            }
        }else if(orientation == LinearLayoutManager.VERTICAL) {
            int left = parent.getPaddingLeft();
            int pRight = parent.getPaddingRight();
            int right = parent.getWidth() - pRight;
            int rows = (childCount - 1);
            int spanCount = 1;
            for (int i = 0; i < rows; ++i) {
                int index = i * spanCount;
                final View child = parent.getChildAt(index);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int mCBottom = params.bottomMargin;
                top = child.getBottom() + mInsetsBottom + mCBottom + Math.round(ViewCompat.getTranslationY(child));
                if (top < parent.getPaddingTop())
                    continue;
                bottom = top + 2;
                c.drawRect(left, top, right, bottom, paint);
            }
        }
    }

    private void drawGridDecorater(Canvas c, RecyclerView parent, GridLayoutManager manager) {
        int childCount = parent.getChildCount();
        int spanCount = manager.getSpanCount();
        int rows = (childCount - 1) / spanCount;
        int cols = Math.min(spanCount, childCount);
        int bottom = 0;
        int top = parent.getPaddingTop();
        for (int i = 0; i < cols; ++i) {
            int actureRowIndex = rows;
            int lastIndex = 0;
            if (actureRowIndex * cols + i >= childCount) {
                actureRowIndex--;
            }
            lastIndex = actureRowIndex * cols + i;
            View child = parent.getChildAt(lastIndex);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int mCRight = params.rightMargin;
            int mCBottom = params.bottomMargin;

            bottom = child.getBottom() + mInsetsBottom + mCBottom;
            int left = child.getRight() + mCRight + mInsetsRight;
            int right = left + 2;
            c.drawRect(left, top, right, bottom, paint);
        }

        int left = parent.getPaddingLeft();
        int pRight = parent.getPaddingRight();
        int right = parent.getWidth() - pRight;
        for (int i = 0; i < rows; ++i) {
            int index = i * spanCount;
            final View child = parent.getChildAt(index);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int mCBottom = params.bottomMargin;
            top = child.getBottom() + mInsetsBottom + mCBottom + Math.round(ViewCompat.getTranslationY(child));
            if (top < parent.getPaddingTop())
                continue;
            bottom = top + 2;
            c.drawRect(left, top, right, bottom, paint);
        }
    }
}
