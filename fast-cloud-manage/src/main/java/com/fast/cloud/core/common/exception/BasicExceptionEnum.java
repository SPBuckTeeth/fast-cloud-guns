package com.fast.cloud.core.common.exception;

import com.fast.cloud.core.exception.ServiceExceptionEnum;

/**
 * @author lossdate
 * @Description 基本业务异常的枚举
 */
public enum BasicExceptionEnum implements ServiceExceptionEnum {

    /**
     * 基本异常
     */
    NULL_NUM(800, "数量不能为空"),
    ERROR_PATTERN(801, "数量格式有误");

    BasicExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
