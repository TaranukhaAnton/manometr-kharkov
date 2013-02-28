package org.krams.tutorial.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: andrew
 * Date: 16.10.2009
 */
@Entity
@Table(name = "currency")
public class Currency extends EnumModel {
    @Column(name = "symbol_code", updatable = false)
    private int symbolCode;

    public void copyTo(Currency copy) {
        super.copyTo(copy);
        copy.symbolCode = symbolCode;
    }
        
    public String getSymbol() {
        return new String(new char[]{(char)symbolCode});
    }

    public int getSymbolCode() {
        return symbolCode;
    }

    public void setSymbolCode(int symbolCode) {
        this.symbolCode = symbolCode;
    }
}
