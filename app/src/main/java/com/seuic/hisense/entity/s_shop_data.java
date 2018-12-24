package com.seuic.hisense.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Administrator on 2015-11-01.
 */

@XStreamAlias("SHOP_DATA")
public class s_shop_data {

    private String ORGCODE  = "";//组织编码
    private String ORGNAME  = "";//>组织名称
    private String ORGTYPE  = "";//组织类型 0-自营店 1-加盟店 2-配送中心
    private String ISDBDEPLOY  = "";//0 - 否  1 - 是


    public String getORGCODE() {
        return ORGCODE;
    }

    public void setORGCODE(String ORGCODE) {
        this.ORGCODE = ORGCODE;
    }

    public String getORGNAME() {
        return ORGNAME;
    }

    public void setORGNAME(String ORGNAME) {
        this.ORGNAME = ORGNAME;
    }

    public String getORGTYPE() {
        return ORGTYPE;
    }

    public void setORGTYPE(String ORGTYPE) {
        this.ORGTYPE = ORGTYPE;
    }

    public String getISDBDEPLOY() {
        return ISDBDEPLOY;
    }

    public void setISDBDEPLOY(String ISDBDEPLOY) {
        this.ISDBDEPLOY = ISDBDEPLOY;
    }
}
