package com.avrora.exceptions;

public class incorrectChecksumException extends Exception{
	public incorrectChecksumException(String str) {
        super(str);
    }


    public incorrectChecksumException() {
        super();
    }

}
