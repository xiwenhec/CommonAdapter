package com.sivin.adapter.section;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.sivin.adapter.section.Render.SectionRender;
import com.sivin.adapter.section.caching.SectionProvider;
import com.sivin.adapter.section.caching.SectionViewCache;
import com.sivin.adapter.section.calculation.DimensionCalculator;
import com.sivin.adapter.section.calculation.SectionPositionCalculator;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

/**
 * Created by Sivin on 2017/2/16.
 */

public class SectionDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "SectionDecoration";

    private ISectionAdapter mSectionAdapter;

    private final Rect mTempRect = new Rect();

    private final SectionRender mRender;

    private final SectionPositionCalculator mPostionCalculator;

    private final SectionProvider mSectionProvider;

    private final DimensionCalculator mDimensionCalculator;

    //存放Rect的集合
    private final SparseArray<Rect> mSectionRects = new SparseArray<>();



    public SectionDecoration(ISectionAdapter sectionAdapter) {
        super();
        mSectionAdapter = sectionAdapter;
        mDimensionCalculator = new DimensionCalculator();
        mRender = new SectionRender(mDimensionCalculator);
        mPostionCalculator = new SectionPositionCalculator(sectionAdapter);
        mSectionProvider = new SectionViewCache(sectionAdapter);

    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int itemPosition = parent.getChildLayoutPosition(view);
        if (itemPosition == NO_POSITION) {
            return;
        }
        //判断时候有新的头
        if (mPostionCalculator.hasNewSection(itemPosition)) {
            //获取一个section
            View section = mSectionProvider.getSection(parent, itemPosition);
            setItemOffsetsForHeader(outRect, section);
        }
    }

    private void setItemOffsetsForHeader(Rect itemOffsets, View header) {
        //设置margin
        mDimensionCalculator.initMargins(mTempRect, header);
        itemOffsets.top = header.getHeight() + mTempRect.top + mTempRect.bottom;
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        final int childCount = parent.getChildCount();
        if (childCount <= 0 || mSectionAdapter.getItemCount() <= 0) {
            return;
        }

        for (int i = 0; i < childCount; i++) {

            View itemView = parent.getChildAt(i);
            int position = parent.getChildLayoutPosition(itemView);
            if (position == RecyclerView.NO_POSITION) {
                continue;
            }

           if (mPostionCalculator.hasNewSection(position)) {

                View section = mSectionProvider.getSection(parent, position);

                Rect sectionOffset = mSectionRects.get(position);

                if (sectionOffset == null) {

                    sectionOffset = new Rect();
                    mSectionRects.put(position, sectionOffset);
                }

              initDefaultSectionOffset(sectionOffset,parent,section,itemView);
                mRender.drawHeader(parent, c, section, sectionOffset);
            }
        }
    }






    private void initDefaultSectionOffset(Rect sectionMargins, RecyclerView recyclerView, View header, View itemView) {

        Rect mTempRect1 = new Rect();

        int translationX, translationY;

        mDimensionCalculator.initMargins(mTempRect1, header);

        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();

        int leftMargin = 0;
        int topMargin = 0;

        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {

            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;

            leftMargin = marginLayoutParams.leftMargin;

            topMargin = marginLayoutParams.topMargin;

        }

        //section 的left位置是itmeView的left-leftmargin+section的margin
        translationX = itemView.getLeft() - leftMargin + mTempRect1.left;

        //section 的 top的位置是itemView的top位置是itemView的top的位置-marginTo-
//        translationY = Math.max(
//                itemView.getTop() - topMargin - header.getHeight() - mTempRect1.bottom,
//                getListTop(recyclerView) + mTempRect1.top);
        translationY = itemView.getTop() - topMargin - header.getHeight() - mTempRect1.bottom;

        sectionMargins.set(translationX, translationY, translationX + header.getWidth(),
                translationY + header.getHeight());
    }

    private int getListTop(RecyclerView view) {
        if (view.getLayoutManager().getClipToPadding()) {
            return view.getPaddingTop();
        } else {
            return 0;
        }
    }

}