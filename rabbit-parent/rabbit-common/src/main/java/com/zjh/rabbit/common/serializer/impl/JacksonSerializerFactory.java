package com.zjh.rabbit.common.serializer.impl;

import com.zjh.rabbit.api.Message;
import com.zjh.rabbit.common.serializer.Serializer;
import com.zjh.rabbit.common.serializer.SerializerFactory;

/**
 * @author zhaojh
 * @date 2021/1/21 14:27
 */
public class JacksonSerializerFactory implements SerializerFactory {

    public static final JacksonSerializerFactory INSTANCE = new JacksonSerializerFactory();

    @Override
    public Serializer create() {
        return JacksonSerializer.createParametricType(Message.class);
    }
}
