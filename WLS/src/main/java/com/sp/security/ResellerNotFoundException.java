package com.sp.security;

import org.springframework.dao.DataAccessException;

/**
 * User: andrew
 * Date: 27.11.2009
 */
public class ResellerNotFoundException extends DataAccessException {
    public ResellerNotFoundException(String s) {
        super(s);
    }
}
