package com.sivin.adapter.section;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sivin.adapter.MultiItemTypeAdapter;
import com.sivin.adapter.base.ViewHolder;

import java.util.List;

/**
 *
 * Created by Sivin on 2017/2/17.
 */

public abstract class SectionAdapter<T extends ISectionBean> extends MultiItemTypeAdapter implements ISectionAdapter {


    private int mSectionLayoutId;
    private List<T> mSectionList;
    private Context mContext;


    public SectionAdapter(Context context, int sectionLayoutId, List data) {
        super(context, data);
        mSectionLayoutId = sectionLayoutId;
        mSectionList = data;
        mContext = context;
    }


    @Override
    public long getSectionId(int position) {
        return mSectionList.get(position).getSectionId();
    }

    @Override
    public ViewHolder onCreateSectionViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(mSectionLayoutId, parent, false);
        return new ViewHolder(mContext,view);
    }

    @Override
    public void onBindSectionViewHolder(ViewHolder holder, int position) {
        convertSection(holder, mSectionList.get(position).getSectionTitle());
    }

    protected abstract void convertSection(ViewHolder holder, String tagValue);


}
