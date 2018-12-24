package com.seuic.hisense.httpService;


/**
 * @author Administrator
 * @date 2016/5/11.
 */
public interface IServiceCallback {
    void addUserCallback(boolean b);

    void loginCallback(String result);

    void INavOperateIntfCallback(String result);

}
