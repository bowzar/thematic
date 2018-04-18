package com.yulintu.thematic.data;

import lombok.Getter;

@Getter
public class DataException extends RuntimeException {

    //region fields
    private Object data;
    //endregion

    //region ctor
    public DataException(String message, Object data, Throwable e) {
        super(message, e);
        this.data = data;
    }

    public DataException(String message, Object data) {
        this(message, data, null);
    }

    public DataException(String message) {
        this(message, null, null);
    }

    public DataException(String message, Throwable e) {
        this(message, null, e);
    }

    public DataException(Throwable e) {
        this(null, null, e);
    }

    public DataException() {
        this(null, null, null);
    }
    //endregion

}
