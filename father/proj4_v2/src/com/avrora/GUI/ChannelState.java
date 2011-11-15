package com.avrora.GUI;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
public class ChannelState {
    @Id
    int id;
    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    boolean kran0;
    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    boolean kran1;
    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    boolean kran2;
    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    boolean kran3;
    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    boolean kran4;
    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    boolean kran5;
    @Column(nullable = false, length = 1)
    @Type(type = "yes_no")
    boolean kran6;

    public ChannelState() {
    }

    public ChannelState(int id, boolean kran0, boolean kran1, boolean kran2, boolean kran3, boolean kran4, boolean kran5, boolean kran6) {
        this.id = id;
        this.kran0 = kran0;
        this.kran1 = kran1;
        this.kran2 = kran2;
        this.kran3 = kran3;
        this.kran4 = kran4;
        this.kran5 = kran5;
        this.kran6 = kran6;
    }

    public boolean isKran0() {
        return kran0;
    }

    public void setKran0(boolean kran0) {
        this.kran0 = kran0;
    }

    public boolean isKran6() {
        return kran6;
    }

    public void setKran6(boolean kran6) {
        this.kran6 = kran6;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isKran1() {
        return kran1;
    }

    public void setKran1(boolean kran1) {
        this.kran1 = kran1;
    }

    public boolean isKran2() {
        return kran2;
    }

    public void setKran2(boolean kran2) {
        this.kran2 = kran2;
    }

    public boolean isKran3() {
        return kran3;
    }

    public void setKran3(boolean kran3) {
        this.kran3 = kran3;
    }

    public boolean isKran4() {
        return kran4;
    }

    public void setKran4(boolean kran4) {
        this.kran4 = kran4;
    }

    public boolean isKran5() {
        return kran5;
    }

    public void setKran5(boolean kran5) {
        this.kran5 = kran5;
    }

    public void setKran(int num, boolean state) {
        switch (num) {
            case 0:
                kran0 = state;
                break;
            case 1:
                kran1 = state;
                break;
            case 2:
                kran2 = state;
                break;
            case 3:
                kran3 = state;
                break;
            case 4:
                kran4 = state;
                break;
            case 5:
                kran5 = state;
                break;
        }

    }

    public boolean getKran(int num) {
        switch (num) {
            case 0:
                return kran0;
            case 1:
                return kran1;
            case 2:
                return kran2;
            case 3:
                return kran3;
            case 4:
                return kran4;
            case 5:
                return kran5;
            default:
                return false;
        }

    }


}
