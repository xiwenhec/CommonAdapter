package com.sivin.adapter.Test.bean;

/**
 * Created by Sivin on 2017/2/16.
 */

public class ItemBean1 extends BaseBean{

    private String content;

    private String tagValue;

    public ItemBean1(int tagId, String content, String tagValue) {
        super(tagId);
        this.content = content;
        this.tagValue = tagValue;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }
}
