package com.sp.dto.mobile;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by IntelliJ IDEA.
 * User: Oleg
 * Date: 30.08.12
 * Time: 12:46
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "userDetails")
public class UserDetailsMobile {
    
    private String userName;
    private  String password;

    public UserDetailsMobile() {}

    public UserDetailsMobile(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
