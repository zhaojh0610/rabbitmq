package com.zjh.rabbit.common.constant;

/**
 * $BrokerMessageStatus 消息的发送状态
 *
 * @author zhaojh
 * @date 2021/1/24 9:36
 */
public enum BrokerMessageStatus {

    SEND_ING("0"),
    SEND_OK("1"),
    SEND_FAIL("2");

    /**
     * brokerMessage 消息状态编码
     */
    private String code;

    private BrokerMessageStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
