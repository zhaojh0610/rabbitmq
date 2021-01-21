package com.zjh.rabbit.common.serializer;

/**
 * 序列化反序列化接口
 *
 * @author zhaojh
 * @date 2021/1/21 11:29
 */
public interface Serializer {

    /**
     * 序列化成byte数组
     *
     * @param data
     * @return
     */
    byte[] serializeRaw(Object data);

    /**
     * 序列化成字符传
     *
     * @param data
     * @return
     */
    String serialize(Object data);

    /**
     * 数组反序列化
     *
     * @param content
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] content);

    /**
     * 字符串反序列化
     *
     * @param content
     * @param <T>
     * @return
     */
    <T> T deserialize(String content);


}
