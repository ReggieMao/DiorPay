package com.gene.library.exception;

/**
 * Created by MaoLJ on 2018/7/18.
 * api异常处理
 */

public class ApiException extends Exception {

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ApiException(Throwable e, int code) {
        super(e);
        this.code = code;
        this.message = e.getMessage();
    }

}
