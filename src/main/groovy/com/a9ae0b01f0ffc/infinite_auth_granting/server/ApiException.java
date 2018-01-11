package com.a9ae0b01f0ffc.infinite_auth_granting.server;

public class ApiException extends Exception {
    private int code;

    public ApiException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}
