package com.sivin.adapter.section;

import android.view.ViewGroup;

import com.sivin.adapter.base.ViewHolder;

/**
 *
 * Created by Sivin on 2017/2/16.
 */

public interface ISectionAdapter<VH extends ViewHolder>{

    long getSectionId(int position);

    VH onCreateSectionViewHolder(ViewGroup parent);

    void onBindSectionViewHolder(VH holder, int position);


    /**
     * 是adapter里的方法，正常情况下，我们的子类应该继承Apater,然后实现这个接口，从而调用到这个方法
     *
     * @return 返回适配器中的Item的个数
     */
    int getItemCount();

}
