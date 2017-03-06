package com.sivin.adapter.Test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sivin.adapter.Test.bean.ItemBean1;

import java.util.List;

/**
 * Created by Sivin on 2017/2/16.
 */

public class TitleItemDecoration extends RecyclerView.ItemDecoration {
    private List<ItemBean1> mDatas;

    //存放Rect的集合
    private final SparseArray<Rect> mHeaderRects = new SparseArray<>();

    /**
     * 绘制title的画笔
     */
    private Paint mPaint;

    /**
     * 存放测量文字的Rect
     */
    private Rect mBounds;//用于存放测量文字Rect

    /**
     * title的高度
     */
    private int mTitleHeight;

    /**
     * title的背景颜色
     */
    private static int COLOR_TITLE_BG = Color.parseColor("#FFDFDFDF");

    /**
     * title文字颜色
     */
    private static int COLOR_TITLE_FONT = Color.parseColor("#FF000000");

    /**
     * title的文字大小
     */
    private static int mTitleFontSize;

    private final Rect mTempRect = new Rect();


    public TitleItemDecoration(Context context, List<ItemBean1> datas) {
        super();
        mDatas = datas;
        mPaint = new Paint();
        mBounds = new Rect();

        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        mTitleFontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics());

        mPaint.setTextSize(mTitleFontSize);

        mPaint.setAntiAlias(true);
    }


    /**
     * 我们需要利用 parent和state变量，来获取需要的辅助信息，例如绘制的上下左右，childCount， childView等。。
     * 最终利用c调用Canvas的方法来绘制出我们想要的UI。会自定义View就会写本方法~

     onDraw绘制出的内容是在ItemView下层，虽然它可以绘制超出getItemOffsets()里的Rect区域，
     但是超出区域最终不会显示，但被ItemView覆盖的区域会产生OverDraw。

     本文如下编写：通过parent获取绘制UI的 left和right以及childCount，
     遍历childView，根据childView的postion，和方法一中的判断方法一样，来决定是否绘制分类Title区域：

     分类绘制title的方法就是自定义View的套路，根据确定的上下左右范围先drawRect绘制一个背景，然后drawText绘制文字。
     * @param c
     * @param parent
     * @param state
     */

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);


        //判断是否需要头

        final int left = parent.getPaddingLeft();

        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {

            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();

            int position = params.getViewLayoutPosition();

            //我记得Rv的item position在重置时可能为-1.保险点判断一下吧
            if (position > -1) {
                if (position == 0) {//等于0肯定要有title的
                    drawTitleArea(c, left, right, child, params, position);

                }
                else if(position >= mDatas.size()){

                }
                else {//其他的通过判断
                    if (mDatas.get(position).getTagId()>=0 && mDatas.get(position).getTagId()!=mDatas.get(position - 1).getTagId()) {
                        //不为空 且跟前一个tag不一样了，说明是新的分类，也要title
                        drawTitleArea(c, left, right, child, params, position);
                    } else {
                        //none
                    }
                }
            }
        }
    }



    /**
     * Draws a header to a canvas, offsetting by some x and y amount
     *
     * @param recyclerView the parent recycler view for drawing the header into
     * @param canvas       the canvas on which to draw the header
     * @param header       the view to draw as the header
     * @param offset       a Rect used to define the x/y offset of the header. Specify x/y offset by setting
     *                     the {@link Rect#left} and {@link Rect#top} properties, respectively.
     */
    public void drawHeader(RecyclerView recyclerView, Canvas canvas, View header, Rect offset) {
        canvas.save();

        if (recyclerView.getLayoutManager().getClipToPadding()) {
            // Clip drawing of headers to the padding of the RecyclerView. Avoids drawing in the padding
            initClipRectForHeader(mTempRect, recyclerView, header);
            canvas.clipRect(mTempRect);
        }

        canvas.translate(offset.left, offset.top);

        header.draw(canvas);
        canvas.restore();
    }

    public void initMargins(Rect margins, View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            initMarginRect(margins, marginLayoutParams);
        } else {
            margins.set(0, 0, 0, 0);
        }
    }

    private void initMarginRect(Rect marginRect, ViewGroup.MarginLayoutParams marginLayoutParams) {
        marginRect.set(
                marginLayoutParams.leftMargin,
                marginLayoutParams.topMargin,
                marginLayoutParams.rightMargin,
                marginLayoutParams.bottomMargin
        );
    }

    private void initClipRectForHeader(Rect clipRect, RecyclerView recyclerView, View header) {
        initMargins(clipRect, header);


        if (getOrientation(recyclerView) == LinearLayout.VERTICAL) {

            clipRect.set(
                    recyclerView.getPaddingLeft(),
                    recyclerView.getPaddingTop(),
                    recyclerView.getWidth() - recyclerView.getPaddingRight() - clipRect.right,
                    recyclerView.getHeight() - recyclerView.getPaddingBottom());

        } else {

            clipRect.set(
                    recyclerView.getPaddingLeft(),
                    recyclerView.getPaddingTop(),
                    recyclerView.getWidth() - recyclerView.getPaddingRight(),
                    recyclerView.getHeight() - recyclerView.getPaddingBottom() - clipRect.bottom);

        }
    }



    public int getOrientation(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        throwIfNotLinearLayoutManager(layoutManager);
        return ((LinearLayoutManager) layoutManager).getOrientation();
    }

    private void throwIfNotLinearLayoutManager(RecyclerView.LayoutManager layoutManager){
        if (!(layoutManager instanceof LinearLayoutManager)) {
            throw new IllegalStateException("StickyListHeadersDecoration can only be used with a " +
                    "LinearLayoutManager.");
        }
    }


    /**
     * 绘制Title区域背景和文字的方法
     *
     * @param c
     * @param left
     * @param right
     * @param child
     * @param params
     * @param position
     */
    private void drawTitleArea(Canvas c, int left, int right, View child, RecyclerView.LayoutParams params, int position) {//最先调用，绘制在最下层
        mPaint.setColor(COLOR_TITLE_BG);
        c.drawRect(left, child.getTop() - params.topMargin - mTitleHeight, right, child.getTop() - params.topMargin, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);

        mPaint.getTextBounds(mDatas.get(position).getTagValue(), 0, mDatas.get(position).getTagValue().length(), mBounds);

        c.drawText(mDatas.get(position).getTagValue(), child.getPaddingLeft(), child.getTop() - params.topMargin - (mTitleHeight / 2 - mBounds.height() / 2), mPaint);
    }



    /**
     * 我们需要利用 parent和state变量，来获取需要的辅助信息，例如postion，
     * 最终调用outRect.set(int left, int top, int right, int bottom)方法，
     * 设置四个方向上 需要为ItemView设置padding的值。
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();

        //我记得Rv的item position在重置时可能为-1.保险点判断一下吧
        if (position > -1) {
            if (position == 0) {//等于0肯定要有title的
                outRect.set(0, mTitleHeight, 0, 0);
            }
            else if(position >= mDatas.size()){

            }
            else {//其他的通过判断
                if (mDatas.get(position).getTagId()>=0 && mDatas.get(position).getTagId()!=mDatas.get(position - 1).getTagId()) {
                    //不为空 且跟前一个tag不一样了，说明是新的分类，也要title
                    outRect.set(0, mTitleHeight, 0, 0);
                } else {
                    outRect.set(0, 0, 0, 0);
                }
            }
        }
    }

}