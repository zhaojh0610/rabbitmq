package com.zjh.rabbit.common.constant;

/**
 * BrokerMessageConstant  常用变量
 *
 * @author zhaojh
 * @date 2021/1/24 10:11
 */
public enum BrokerMessageConstant {

    TIMEOUT(1);

    private int code;

    private BrokerMessageConstant(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

}
