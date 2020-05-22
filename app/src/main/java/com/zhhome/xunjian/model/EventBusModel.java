package com.zhhome.xunjian.model;

/**
 * Created by Norton on 2018/9/11.
 */

public class EventBusModel<T>  {
    private String tag;
    private T params;

    public EventBusModel(String tag, T params) {
        this.tag = tag;
        this.params = params;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }
}
