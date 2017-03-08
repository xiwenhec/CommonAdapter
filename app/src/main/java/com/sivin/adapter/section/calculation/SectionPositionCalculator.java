package com.sivin.adapter.section.calculation;


import com.sivin.adapter.section.ISectionAdapter;

public class SectionPositionCalculator {


    private ISectionAdapter mSectionAdapter;


    public SectionPositionCalculator(ISectionAdapter mSectionAdapter) {
        this.mSectionAdapter = mSectionAdapter;
    }

    public boolean hasNewSection(int position) {
        if (indexOutOfBounds(position)) {
            return false;
        }

        //第一个总是有一个Section
        if (position == 0 && !(mSectionAdapter.getSectionId(position) < 0)) {

            return true;
        }
        //获取头的id
        long currentSectionId = mSectionAdapter.getSectionId(position);

        //获取前一个SectionId
        long previousSectionId = mSectionAdapter.getSectionId(position - 1);

        if (currentSectionId < 0 || previousSectionId < 0) {
            return false;
        }

        //当前的这个item的Section不等于上一个Section时，表示出现了一个新的section
        if (currentSectionId != previousSectionId) {
            return true;
        }
        return false;
    }


    /**
     * 判断是否越界
     * @param position
     * @return
     */
    private boolean indexOutOfBounds(int position) {
        return position < 0 || position >= mSectionAdapter.getItemCount();
    }



}
