package com.sp.dto;

import org.postgis.PGgeometry;

import java.sql.SQLException;

/**
 * Please, write comment here
 * Created by Oleg Golushkov
 * Date: 27.07.2009
 */
public class StreetDetailsDto {
    private String name;

    private String name0;

    private String district;

    private String postcode;

    private String wktPoint;

    private double distance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName0() {
        return name0;
    }

    public void setName0(String name0) {
        this.name0 = name0;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWktPoint() {
        return wktPoint;
    }

    public void setWktPoint(String wktPoint) {
        this.wktPoint = wktPoint;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public ClosestStreetDetails buildClosestStreetsDeatils() {
        ClosestStreetDetails details = new ClosestStreetDetails();
        StringBuffer address = new StringBuffer();
        if (this.name != null){
            String name = "Unnamed Road";
            if (!this.name.equals("")){
                name = this.name;
            }
            address.append(name).append(", ");
        }
        if (this.name0 != null && !this.name0.equals("")){
            address.append("(").append(this.name0).append("), ");
        }
        if (this.district != null && !this.district.equals("")){
            address.append(this.district);
        }

        details.setAddress(address.toString());

        if (this.wktPoint != null && !this.wktPoint.equals("")){
            try {
                PGgeometry geometry = new PGgeometry(this.wktPoint);
                details.setLon(geometry.getGeometry().getPoint(0).getX());
                details.setLat(geometry.getGeometry().getPoint(0).getY());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (this.distance > 0){
            details.setDistance(this.distance);
        }

        details.setPostcode(postcode);

        return details;
    }
}
