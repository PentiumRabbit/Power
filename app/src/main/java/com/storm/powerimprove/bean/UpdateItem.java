
package com.storm.powerimprove.bean;


import com.android.base.common.bean.BaseNetJson;
import com.google.gson.annotations.Expose;
import com.storm.powerimprove.bean.Content.UpdateContent;

/**
 * 软件更新
 */
public class UpdateItem extends BaseNetJson {


    @Expose
    private UpdateContent content;


    public UpdateContent getContent() {
        return content;
    }

    public void setContent(UpdateContent content) {
        this.content = content;
    }


}
