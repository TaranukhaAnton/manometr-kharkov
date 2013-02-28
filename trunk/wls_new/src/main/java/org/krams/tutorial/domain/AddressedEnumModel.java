package org.krams.tutorial.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@MappedSuperclass
public class AddressedEnumModel extends EnumModel {
    @Column(name = "cur_street", updatable = false)
    private String curStreet;


    @Column(name = "cur_postcode", updatable = false)
    private String curPostcode;

    public void copyTo(AddressedEnumModel copy) {
        super.copyTo(copy);
        copy.curStreet = curStreet;
        copy.curPostcode = curPostcode;
    }

    public void setCurAddress(String curAddress) {
    }

    public String getCurAddress() {
        // return Util.getJoinedStrs(curStreet,curPostcode);
        //todo fix
        return "";
    }

    public String getCurStreet() {
        return curStreet;
    }

    public void setCurStreet(String curStreet) {
        this.curStreet = curStreet;
    }

    public String getCurPostcode() {
        return curPostcode;
    }

    public void setCurPostcode(String curPostcode) {
        this.curPostcode = curPostcode;
    }
}
