package com.yulintu.thematic.web;

public class ApiException extends RuntimeException {

    //region fields
    protected int status;
    protected Object data;
    //endregion

    //region ctor
    public ApiException(int status, String message, Object data, Throwable e) {
        super(message, e);
        this.status = status;
        this.data = data;
    }

    public ApiException(int status, String message, Object data) {
        this(status, message, data, null);
    }

    public ApiException(int status, String message) {
        this(status, message, null, null);
    }

    public ApiException(String message, Throwable e) {
        this(0, message, null, e);
    }

    public ApiException(Throwable e) {
        super(e);
    }

    public ApiException() {

    }
    //endregion

    //region methods
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    //endregion
}