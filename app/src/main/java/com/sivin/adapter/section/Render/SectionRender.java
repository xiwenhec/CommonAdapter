package com.sivin.adapter.section.Render;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sivin.adapter.section.calculation.DimensionCalculator;

/**
 * Created by Sivin on 2017/2/16.
 */

public class SectionRender {

    private final Rect mTempRect = new Rect();

    private final DimensionCalculator mDimensionCalculator;


    public SectionRender(DimensionCalculator mDimensionCalculator) {
        this.mDimensionCalculator = mDimensionCalculator;
    }

    public void drawHeader(RecyclerView recyclerView, Canvas canvas, View header, Rect offset) {
        canvas.save();
        if (recyclerView.getLayoutManager().getClipToPadding()) {
            initClipRectForHeader(mTempRect, recyclerView, header);
            canvas.clipRect(mTempRect);
        }

        canvas.translate(offset.left, offset.top);
        header.draw(canvas);
        canvas.restore();
    }


    private void initClipRectForHeader(Rect clipRect, RecyclerView recyclerView, View header) {
        mDimensionCalculator.initMargins(clipRect, header);
        clipRect.set(
                recyclerView.getPaddingLeft(),
                recyclerView.getPaddingTop(),
                recyclerView.getWidth() - recyclerView.getPaddingRight() - clipRect.right,
                recyclerView.getHeight() - recyclerView.getPaddingBottom());
    }


}
