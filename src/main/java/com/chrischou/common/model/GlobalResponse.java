package com.chrischou.common.model;

import com.chrischou.enums.RespStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

public class GlobalResponse<T> {
    private RespStatus status;
    private String message;
    private T data;

    /**
     * Default no data success response
     */
    public GlobalResponse() {
        this(RespStatus.SUCCESS, null, null);
    }

    /**
     * Default success response with data
     */
    public GlobalResponse(T data) {
        this(RespStatus.SUCCESS, null, data);
    }

    /**
     * Default response for failed operation
     */
    public GlobalResponse(RespStatus status, String message) {
        this(status, message, null);
    }

    public GlobalResponse(RespStatus status, String message, T data) {
        this.data = data;
        this.status = status;
        this.message = StringUtils.isNoneBlank(message) ? message : status.getDesc();
    }

    public int getCode() {
        return status.getCode();
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

//    @JsonProperty("code")
    public void setCode(int code) {
        this.status = RespStatus.get(code);
    }

}
