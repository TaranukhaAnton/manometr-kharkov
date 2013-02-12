package com.sp.dto.mobile;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Oleg
 * Date: 28.08.12
 * Time: 12:04
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "unitViews")
public class UnitViewListMobile extends MobileSerializableList {
    private int count;
    private List<UnitViewMobile> unitViewMobiles;

    public UnitViewListMobile() {}

    public UnitViewListMobile(List<UnitViewMobile> unitViewMobiles) {
        this.unitViewMobiles = unitViewMobiles;
        this.count = unitViewMobiles.size();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @XmlElement(name = "unitView")
    public List<UnitViewMobile> getUnitViewMobiles() {
        return unitViewMobiles;
    }

    public void setUnitViewMobiles(List<UnitViewMobile> unitViewMobiles) {
        this.unitViewMobiles = unitViewMobiles;
    }
}
