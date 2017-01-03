package com.gui.lgrecycleviewdecorator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by guizhigang on 16/12/30.
 */
public class LGRecycleViewDecorator extends RecyclerView.ItemDecoration {
    private Context context;
    private Rect mInsetsRect = new Rect(10, 10, 10, 10);

    private static final int[] ATTRS = {android.R.attr.listDivider};
    private Drawable divider;

    public LGRecycleViewDecorator(Context context) {
        this.context = context;
        TypedArray a = context.obtainStyledAttributes(ATTRS);
        divider = a.getDrawable(0);
        a.recycle();
    }

    public LGRecycleViewDecorator(Context context, int drawableId) {
        this.context = context;
        divider = ContextCompat.getDrawable(context, drawableId);
    }

    public void setmInsets(int left, int top, int right, int bottom) {
        mInsetsRect.set(left, top, right, bottom);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mInsetsRect.left, mInsetsRect.top, mInsetsRect.right, mInsetsRect.bottom);
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
            drawLinearDecorater(c, parent, (LinearLayoutManager) manager);
        }
    }

    private void drawLinearDecorater(Canvas c, RecyclerView parent, LinearLayoutManager manager) {
        int childCount = parent.getChildCount();
        int orientation = manager.getOrientation();
        int top = parent.getPaddingTop();
        int bottom = 0;
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            for (int i = 0; i < childCount; ++i) {
                View child = parent.getChildAt(i);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                int mCRight = params.rightMargin;
                int mCBottom = params.bottomMargin;

                bottom = child.getBottom() + mInsetsRect.bottom + mCBottom;
                int left = child.getRight() + mCRight + mInsetsRect.right;
                int right = left + divider.getIntrinsicWidth();
                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
            }
        } else if (orientation == LinearLayoutManager.VERTICAL) {
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
                top = child.getBottom() + mInsetsRect.bottom + mCBottom + Math.round(ViewCompat.getTranslationY(child));
                bottom = top + divider.getIntrinsicHeight();
                if (top < parent.getPaddingTop() || bottom >= parent.getHeight() - parent.getPaddingBottom())
                    continue;
                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
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
            int actureRow = rows;
            int lastIndex;
            if (actureRow * cols + i >= childCount) {
                actureRow--;
            }
            lastIndex = actureRow * cols + i;
            View child = parent.getChildAt(lastIndex);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int mCRight = params.rightMargin;
            int mCBottom = params.bottomMargin;

            bottom = Math.min(child.getBottom() + mInsetsRect.bottom + mCBottom, parent.getHeight() - parent.getPaddingBottom());
            int left = child.getRight() + mCRight + mInsetsRect.right;
            int right = left + divider.getIntrinsicWidth();
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }

        int left = parent.getPaddingLeft();
        int pRight = parent.getPaddingRight();
        int right = parent.getWidth() - pRight;
        for (int i = 0; i < rows; ++i) {
            int index = i * spanCount;
            final View child = parent.getChildAt(index);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int mCBottom = params.bottomMargin;
            top = child.getBottom() + mInsetsRect.bottom + mCBottom + Math.round(ViewCompat.getTranslationY(child));
            bottom = top + divider.getIntrinsicHeight();
            if (top < parent.getPaddingTop() || bottom >= parent.getHeight() - parent.getPaddingBottom()) {
                continue;
            }
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }
}
