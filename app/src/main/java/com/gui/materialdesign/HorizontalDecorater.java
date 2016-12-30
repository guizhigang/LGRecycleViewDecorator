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
import android.support.v7.widget.RecyclerView;
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
        int childCount = parent.getChildCount();
        if (childCount <= 0) return;

        //竖直方向上绘制分隔线
        /*int left = parent.getPaddingLeft();
        int pRight = parent.getPaddingRight();
        int right = parent.getWidth() - pRight;
        int pTop = parent.getTop();
        for (int i = 0; i < childCount; ++i) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int mCLeft = params.leftMargin;
            int mCRight = params.rightMargin;
            int mCTop = params.topMargin;
            int mCBottom = params.bottomMargin;
            int childPaddingBottom = child.getPaddingBottom();

            final int top = child.getBottom() + mInsetsBottom + mCBottom + Math.round(ViewCompat.getTranslationY(child));
            final int bottom = top + 2;
            c.drawRect(left, top, right, bottom, paint);
        }*/

        //水平方向上绘制分隔线

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int childCount = parent.getChildCount();
        if (childCount <= 0) return;
        //水平方向上绘制分隔线
        final int top = parent.getPaddingTop();
        final int pBottom = parent.getPaddingBottom();
        final int bottom = parent.getHeight() - pBottom;
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            int spanCount = ((GridLayoutManager) manager).getSpanCount();
            for (int i = 0; i < spanCount; ++i) {
                final View child = parent.getChildAt(i);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                int mCLeft = params.leftMargin;
                int mCRight = params.rightMargin;
                int mCTop = params.topMargin;
                int mCBottom = params.bottomMargin;
                int childPaddingBottom = child.getPaddingBottom();
                int left = child.getRight() + mCRight + mInsetsRight;
                int right = left + 2;
                int cHight = child.getHeight();

//            final int top = child.getTop() - mInsetsTop;
//            final int bottom = top + child.getHeight() + mInsetsBottom;
                c.drawRect(left, top, right, bottom, paint);
            }
        }
//        int left = parent.getPaddingLeft();
//        int pRight = parent.getPaddingRight();
//        int right = parent.getWidth() - pRight;
//        int pTop = parent.getTop();
//        for (int i = 0; i < childCount; ++i) {
//            final View child = parent.getChildAt(i);
//            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
//            int mCLeft = params.leftMargin;
//            int mCRight = params.rightMargin;
//            int mCTop = params.topMargin;
//            int mCBottom = params.bottomMargin;
//            int childPaddingBottom = child.getPaddingBottom();
//
//            final int top = child.getBottom() + mInsetsBottom + mCBottom + Math.round(ViewCompat.getTranslationY(child));
//            final int bottom = top + 2;
//            c.drawRect(left, top, right, bottom, paint);
//        }
    }
}
