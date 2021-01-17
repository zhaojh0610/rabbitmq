package com.zjh.rabbit.api.exception;

/**
 * @author zhaojh
 * @date 2021/1/17 15:36
 * @desc
 */
public class MessageException extends Exception {

    public MessageException() {
        super();
    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }

}
