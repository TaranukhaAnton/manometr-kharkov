package com.sp.dto.report;

import com.sp.SpContext;
import com.sp.util.Util;

/**
 * Created with IntelliJ IDEA.
 * User: windows7
 * Date: 13.11.12
 * Time: 15:00
 * To change this template use File | Settings | File Templates.
 */
public class DriverLeagueTableRecord extends BaseDriverLeagueTableRecord {

    private double score;
    private double miles;
    private int accelerationOrange;
    private int accelerationRed;
    private int accelerationTotal;
    private int accelerationPoints;
    private String scoreFixed;
    private String milesFixed;

    private int breakingOrange;
    private int breakingRed;
    private int breakingTotal;
    private int breakingPoints;

    private int corneringOrange;
    private int corneringRed;
    private int corneringTotal;
    private int corneringPoints;

    private int totalsOrange;
    private int totalsRed;
    private int totalsTotal;
    private int totalsPoints;
                     // TODO Fields
    public String getScoreFixed() {
        return scoreFixed;// / totalsPoints);
    }

    public void setScoreFixed(String scoreFixed) {}

    public String getMilesFixed() {
        return milesFixed;
    }

    public void setMilesFixed(String milesFixed) { }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getMiles() {
        return miles;
    }

    public void setMiles(double miles) {
        this.miles = miles;
    }

    public int getAccelerationOrange() {
        return accelerationOrange;
    }

    public void setAccelerationOrange(int accelerationOrange) {
        this.accelerationOrange = accelerationOrange;
    }

    public int getAccelerationRed() {
        return accelerationRed;
    }

    public void setAccelerationRed(int accelerationRed) {
        this.accelerationRed = accelerationRed;
    }

    public int getAccelerationTotal() {
        return accelerationTotal;
    }

    public void setAccelerationTotal(int accelerationTotal) {
        this.accelerationTotal = accelerationTotal;
    }

    public int getAccelerationPoints() {
        return accelerationPoints;
    }

    public void setAccelerationPoints(int accelerationPoints) {
        this.accelerationPoints = accelerationPoints;
    }

    public int getBreakingOrange() {
        return breakingOrange;
    }

    public void setBreakingOrange(int breakingOrange) {
        this.breakingOrange = breakingOrange;
    }

    public int getBreakingRed() {
        return breakingRed;
    }

    public void setBreakingRed(int breakingRed) {
        this.breakingRed = breakingRed;
    }

    public int getBreakingTotal() {
        return breakingTotal;
    }

    public void setBreakingTotal(int breakingTotal) {
        this.breakingTotal = breakingTotal;
    }

    public int getBreakingPoints() {
        return breakingPoints;
    }

    public void setBreakingPoints(int breakingPoints) {
        this.breakingPoints = breakingPoints;
    }

    public int getCorneringOrange() {
        return corneringOrange;
    }

    public void setCorneringOrange(int corneringOrange) {
        this.corneringOrange = corneringOrange;
    }

    public int getCorneringRed() {
        return corneringRed;
    }

    public void setCorneringRed(int corneringRed) {
        this.corneringRed = corneringRed;
    }

    public int getCorneringTotal() {
        return corneringTotal;
    }

    public void setCorneringTotal(int corneringTotal) {
        this.corneringTotal = corneringTotal;
    }

    public int getCorneringPoints() {
        return corneringPoints;
    }

    public void setCorneringPoints(int corneringPoints) {
        this.corneringPoints = corneringPoints;
    }

    public int getTotalsOrange() {
        return totalsOrange;
    }

    public void setTotalsOrange(int totalsOrange) {
        this.totalsOrange = totalsOrange;
    }

    public int getTotalsRed() {
        return totalsRed;
    }

    public void setTotalsRed(int totalsRed) {
        this.totalsRed = totalsRed;
    }

    public int getTotalsTotal() {
        return totalsTotal;
    }

    public void setTotalsTotal(int totalsTotal) {
        this.totalsTotal = totalsTotal;
    }

    public int getTotalsPoints() {
        return totalsPoints;
    }

    public void setTotalsPoints(int totalsPoints) {
        this.totalsPoints = totalsPoints;
    }

    public void calculateFields() {

        scoreFixed = Util.decimalFormatter(score * SpContext.getUserPrefs().getDistanceMetricFactor());
        milesFixed = Util.decimalFormatter(miles * SpContext.getUserPrefs().getDistanceMetricFactor());

    }


}
