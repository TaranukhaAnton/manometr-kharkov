package com.sp.model;

import javax.persistence.*;

@Entity
@Table(name = "reseller_handheld_box_type")
public class ResellerHandheldBoxType extends BaseModel {
    @Column(name = "reseller_id")
    private int resellerId;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "box_type_id")
    private HandheldBoxType handheldBoxType;

    public void copyTo(ResellerHandheldBoxType copy) {
        super.copyTo(copy);
        copy.resellerId = resellerId;
        copy.handheldBoxType = handheldBoxType;
    }

    public int getResellerId() {
        return resellerId;
    }

    public void setResellerId(int resellerId) {
        this.resellerId = resellerId;
    }

    public HandheldBoxType getHandheldBoxType() {
        return handheldBoxType;
    }

    public void setHandheldBoxType(HandheldBoxType handheldBoxType) {
        this.handheldBoxType = handheldBoxType;
    }
}
