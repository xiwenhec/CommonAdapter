package com.sivin.adapter.Test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sivin.adapter.MultiItemTypeAdapter;
import com.sivin.adapter.Test.bean.BeanWrapper;
import com.sivin.adapter.base.ViewHolder;
import com.sivin.adapter.section.ISectionAdapter;

import java.util.List;

/**
 *
 * Created by Sivin on 2017/2/17.
 */

public abstract class SectionAdapter extends MultiItemTypeAdapter implements ISectionAdapter {


    private int mSectionLayoutId;
    private List<BeanWrapper> mDatas;
    private Context mContext;


    public SectionAdapter(Context context, int sectionLayoutId, List datas) {
        super(context, datas);
        mSectionLayoutId = sectionLayoutId;
        mDatas = datas;
        mContext = context;
    }


    @Override
    public long getHeaderId(int position) {
        return mDatas.get(position).getTagId();
    }

    @Override
    public ViewHolder onCreateSectionViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(mSectionLayoutId, parent, false);
        return new ViewHolder(mContext,view);
    }

    @Override
    public void onBindSectionViewHolder(ViewHolder holder, int position) {
        convertSection(holder, mDatas.get(position).getTagValue());
    }

    protected abstract void convertSection(ViewHolder holder, String tagValue);


}
