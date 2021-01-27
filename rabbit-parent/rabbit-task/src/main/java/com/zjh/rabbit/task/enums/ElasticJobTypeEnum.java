package com.zjh.rabbit.task.enums;

/**
 * ElasticJobTypeEnum  定时任务枚举类
 *
 * @author zhaojh
 * @date 2021/1/27 20:51
 */
public enum ElasticJobTypeEnum {

    SIMPLE("SimpleJob", "简单类型job"),
    DATAFLOW("DataFlowJob", "流式类型job"),
    SCRIPT("ScriptJob", "脚本类型job");

    private String type;
    private String desc;

    ElasticJobTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
