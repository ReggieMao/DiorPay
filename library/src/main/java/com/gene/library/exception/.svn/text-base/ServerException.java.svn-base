package com.gene.library.exception;

/**
 * Created by MaoLJ on 2018/7/18.
 * 自定义服务器端异常
 */

public class ServerException extends RuntimeException {

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

    public ServerException(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

}
