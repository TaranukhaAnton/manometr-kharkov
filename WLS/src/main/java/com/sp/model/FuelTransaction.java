package com.sp.model;

import com.sp.util.Util;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONE)
@Table(name = "fuel_transaction")
public class FuelTransaction extends BaseModel {

  private static final long serialVersionUID = -5461412777201804455L;

  public static final double MPG_TO_LPHK_CONVERT_COEF = 0.3540062;

  public static final double MPG_CALC_COEF = 4.546;

  @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
  @JoinColumn(name = "unit_id")
  private LightVehicle vehicle;

  @Column(name = "odometer")
  private Long odometer;

  @Column(name = "date_drawn")
  private Date dateDrawn;

  @Column(name = "litres")
  private Double litres;

  @Column(name = "cost")
  private Double cost;

  @Transient
  private Long previousOdometer;

  @Transient
  private Long distance;

  @Transient
  private Long totalTrans;

  @Transient
  private Long totalFuel;

  @Transient
  private Double lphk;

  @Transient
  private Double mpg;

  @Transient
  private Double manufLphk;

  @Transient
  private Double manufMpg;

  @Transient
  private String date;

  @Transient
  private String time;

  public void copyTo(FuelTransaction copy) {
    super.copyTo(copy);
    copy.vehicle = vehicle;
    copy.odometer = odometer;
    copy.dateDrawn = dateDrawn;
    copy.litres = litres;
    copy.cost = cost;
    if (dateDrawn != null) {
      copy.date = Util.getDaysFromDate(dateDrawn);
    }
    if (dateDrawn != null) {
        copy.time = new SimpleDateFormat("HH:mm").format(dateDrawn);
    }

  }

  public Long getOdometer() {
    return odometer;
  }

  public void setOdometer(Long odometer) {
    this.odometer = odometer;
  }

  public Date getDateDrawn() {
    return dateDrawn;
  }

  public void setDateDrawn(Date dateDrawn) {
    this.dateDrawn = dateDrawn;
  }

  public Double getLitres() {
    return litres;
  }

  public void setLitres(Double litres) {
    this.litres = litres;
  }

  public Double getCost() {
    return cost;
  }

  public void setCost(Double cost) {
    this.cost = cost;
  }

  public LightVehicle getVehicle() {
    return vehicle;
  }

  public void setVehicle(LightVehicle vehicle) {
    this.vehicle = vehicle;
  }

  public Long getPreviousOdometer() {
    return previousOdometer;
  }

  public void setPreviousOdometer(Long previousOdometer) {
    this.previousOdometer = previousOdometer;
  }

  public Long getDistance() {
    return distance;
  }

  public void setDistance(Long distance) {
    this.distance = distance;
  }

  public Long getTotalTrans() {
    return totalTrans;
  }

  public void setTotalTrans(Long totalTrans) {
    this.totalTrans = totalTrans;
  }

  public Long getTotalFuel() {
    return totalFuel;
  }

  public void setTotalFuel(Long totalFuel) {
    this.totalFuel = totalFuel;
  }

  public Double getMpg() {
    return mpg;
  }

  public void setMpg(Double mpg) {
    this.mpg = mpg;
  }

  public Double getLphk() {
    return this.lphk;
  }

  public void setLphk(Double lphk) {
    this.lphk = lphk;
  }

  public Double getManufLphk() {
    return manufLphk;
  }

  public void setManufLphk(Double manufLphk) {
    this.manufLphk = manufLphk;
  }

  public Double getManufMpg() {
    return manufMpg;
  }

  public void setManufMpg(Double manufMpg) {
    this.manufMpg = manufMpg;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }
}
