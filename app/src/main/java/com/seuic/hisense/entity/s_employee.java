package com.seuic.hisense.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Administrator on 2015-11-01.
 */
@XStreamAlias("RESULT")
public class s_employee {

    private String RESULT_CODE  = "";//1表示下载成功，0表示下载失败，-1表示下载异常
    private String RESULT_MSG  = "";// 返回的错误信息
    //@XStreamImplicit(itemFieldName = "RESULT_DATA")//隐藏 SHOP_DATA 节点名称
    private List<s_shop_data> RESULT_DATA  = new ArrayList<s_shop_data>();


    public String getRESULT_CODE() {
        return RESULT_CODE;
    }

    public void setRESULT_CODE(String RESULT_CODE) {
        this.RESULT_CODE = RESULT_CODE;
    }

    public String getRESULT_MSG() {
        return RESULT_MSG;
    }

    public void setRESULT_MSG(String RESULT_MSG) {
        this.RESULT_MSG = RESULT_MSG;
    }

    public List<s_shop_data> getRESULT_DATA() {
        return RESULT_DATA;
    }

    public void setRESULT_DATA(List<s_shop_data> RESULT_DATA) {
        this.RESULT_DATA = RESULT_DATA;
    }



}
