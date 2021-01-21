package com.zjh.rabbit.common.serializer;

/**
 * $SerializerFactory
 *
 * @author zhaojh
 * @date 2021/1/21 14:15
 */
public interface SerializerFactory {

    /**
     * 创建自定义的序列化对象
     * @return
     */
    Serializer create();

}
