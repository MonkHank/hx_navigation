package com.seuic.hisense.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2015-11-01.
 */
@XStreamAlias("IMPORTDATA")
public class down_goods_request {

    private int OPERATION = 1;//操作类型 固定值为1
    private int ORDER = 1;//0-按条码排序 1-按编码排序 不填默认按条码排序

    public int getOPERATION() {
        return OPERATION;
    }

    public void setOPERATION(int OPERATION) {
        this.OPERATION = OPERATION;
    }

    public int getORDER() {
        return ORDER;
    }

    public void setORDER(int ORDER) {
        this.ORDER = ORDER;
    }
}
