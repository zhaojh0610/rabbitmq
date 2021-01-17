package com.zjh.rabbit.api;

/**
 * @author zhaojh
 * @date 2021/1/17 15:54
 * @desc 消息投递回调函数
 */
public interface SendCallBack {

    /**
     * 成功处理：回调函数
     */
    void onSuccess();

    /**
     * 失败处理：回调函数
     */
    void onFailure();
}
