package com.sivin.adapter.Test.bean;

import java.util.List;

/**
 * Created by Sivin on 2017/2/17.
 */

public class BeanWrapper {

    private int tagId;

    private String tagValue;


    private ItemBean1 type1Data;

    private ItemBean3 type2Data;

    private List<ItemBean2> list;
    public BeanWrapper(ItemBean1 type1Data) {
        this.type1Data = type1Data;
        tagId = type1Data.getTagId();
        tagValue = type1Data.getTagValue();
    }

    public BeanWrapper(ItemBean3 type2Data) {
        this.type2Data = type2Data;
        tagId = type2Data.getTagId();
        tagValue = type2Data.getTagValue();
    }

    public BeanWrapper(List<ItemBean2> list) {
        this.list = list;

        if(list != null && list.size()>0){
            tagId = list.get(0).getTagId();
            tagValue = list.get(0).getTagValue();
        }

    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagValue() {
        return tagValue;
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
}