package com.sp.exception;

public class ServiceException extends Exception {
    public ServiceException(String s) {
        super(s);
    }

    public ServiceException(Exception e) {
        super(e);
    }
}
