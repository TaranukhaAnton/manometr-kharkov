package com.sp.dto;

import com.sp.util.Constants;

/**
 * User: andrey
 */
public class ImmobilizationResult {
    private int id = -1;
    private boolean status;
    private Constants.ImmobilisationResult result;

    public ImmobilizationResult(int[] toImmobilize) {
        this.status = Boolean.valueOf(String.valueOf(toImmobilize[0]));
        this.result = toImmobilize[1] == -1 ? Constants.ImmobilisationResult.Failed :
                        (toImmobilize[2]== Constants.T6_PREMIUM_BOX_TYPE_ID || toImmobilize[2]== Constants.T8_PREMIUM_BOX_TYPE_ID ? Constants.ImmobilisationResult.InPogress
                        : Constants.ImmobilisationResult.Success);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public boolean isStatus() {
        return status;
    }

    public Constants.ImmobilisationResult getResult() {
        return result;
    }

    public void setResult(Constants.ImmobilisationResult result) {
        this.result = result;
    }

}
