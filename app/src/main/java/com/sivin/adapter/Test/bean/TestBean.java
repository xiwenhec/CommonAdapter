package com.sivin.adapter.Test.bean;

import com.sivin.adapter.section.ISectionBean;

import java.util.List;

/**
 * Created by Sivin on 2017/2/17.
 */

public class TestBean implements ISectionBean{

    private int tagId;

    private String tagValue;


    private ItemBean1 type1Data;

    private ItemBean3 type2Data;

    private List<ItemBean2> list;
    public TestBean(ItemBean1 type1Data) {
        this.type1Data = type1Data;
        tagId = type1Data.getTagId();
        tagValue = type1Data.getTagValue();
    }

    public TestBean(ItemBean3 type2Data) {
        this.type2Data = type2Data;
        tagId = type2Data.getTagId();
        tagValue = type2Data.getTagValue();
    }

    public TestBean(List<ItemBean2> list) {
        this.list = list;

        if(list != null && list.size()>0){
            tagId = list.get(0).getTagId();
            tagValue = list.get(0).getTagValue();
        }

    }


    public void setTagId(int tagId) {
        this.tagId = tagId;
    }



    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }



    public ItemBean1 getType1Data() {
        return type1Data;
    }

    public void setType1Data(ItemBean1 type1Data) {
        this.type1Data = type1Data;
    }

    public ItemBean3 getType3Data() {
        return type2Data;
    }

    public void setType2Data(ItemBean3 type2Data) {
        this.type2Data = type2Data;
    }

    public List<ItemBean2> getList() {
        return list;
    }

    public void setList(List<ItemBean2> list) {
        this.list = list;
    }

    @Override
    public int getSectionId() {
        return tagId;
    }

    @Override
    public String getSectionTitle() {
        return tagValue;
    }
}