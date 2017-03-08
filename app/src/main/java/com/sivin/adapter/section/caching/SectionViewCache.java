package com.sivin.adapter.section.caching;

import android.support.v4.util.LongSparseArray;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.sivin.adapter.base.ViewHolder;
import com.sivin.adapter.section.ISectionAdapter;


public class SectionViewCache implements SectionProvider {

    private final ISectionAdapter mAdapter;

    private final LongSparseArray<View> mSectionViews = new LongSparseArray<>();

    public SectionViewCache(ISectionAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public View getSection(RecyclerView parent, int position) {

        long sectionId = mAdapter.getSectionId(position);

        View section = mSectionViews.get(sectionId);

        if (section == null) {

            ViewHolder viewHolder = mAdapter.onCreateSectionViewHolder(parent);

            mAdapter.onBindSectionViewHolder(viewHolder, position);

            section = viewHolder.itemView;

            if (section.getLayoutParams() == null) {

                section.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }


            int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);


            int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                    parent.getPaddingLeft() + parent.getPaddingRight(), section.getLayoutParams().width);

            int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                    parent.getPaddingTop() + parent.getPaddingBottom(), section.getLayoutParams().height);

            section.measure(childWidth, childHeight);

            section.layout(0, 0, section.getMeasuredWidth(), section.getMeasuredHeight());

            mSectionViews.put(sectionId, section);
        }
        return section;
    }

    @Override
    public void invalidate() {
        mSectionViews.clear();
    }
}
