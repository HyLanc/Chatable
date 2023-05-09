package com.Chatable.enums;

/**
 * @author 韦润泽, 李君哲, 何雨宸, 王开
 * @create 2023-04-30 23:06
 */

/**
 * 好友状态枚举类
 */
public enum FriendStateEnum {

    NORMAL(1,"正常"),

    REMOVE(2,"解除"),

    APPLY(3,"申请"),

    ;

    Integer code;

    String desc;

    FriendStateEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
