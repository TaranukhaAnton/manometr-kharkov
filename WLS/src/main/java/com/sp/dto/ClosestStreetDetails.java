package com.sp.dto;

/**
 * User: andrey
 */
public class ClosestStreetDetails {
    private Double lat;
    private Double lon;
    private String address;
    private String postcode;
    private Double distance;

    public ClosestStreetDetails() {
    }

    public ClosestStreetDetails(String address, String postcode) {
        this.address = address;
        this.postcode = postcode;
    }

    public ClosestStreetDetails(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "ClosestStreetDetails{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", address='" + address + '\'' +
                ", postcode='" + postcode + '\'' +
                ", distance=" + distance +
                '}';
    }
}
