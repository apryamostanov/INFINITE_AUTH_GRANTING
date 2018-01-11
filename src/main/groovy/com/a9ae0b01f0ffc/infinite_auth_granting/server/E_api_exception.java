package com.a9ae0b01f0ffc.infinite_auth_granting.server;

public class E_api_exception extends Exception {
    private int p_code;

    public E_api_exception(int code, String msg) {
        super(msg);
        this.p_code = code;
    }

    public int get_code() {
        return p_code;
    }
}
