package com.zjh.rabbit.api.exception;

/**
 * @author zhaojh
 * @date 2021/1/17 15:39
 * @desc
 */
public class MessageRuntimeException extends RuntimeException{
    public MessageRuntimeException() {
        super();
    }

    public MessageRuntimeException(String message) {
        super(message);
    }

    public MessageRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageRuntimeException(Throwable cause) {
        super(cause);
    }
}
