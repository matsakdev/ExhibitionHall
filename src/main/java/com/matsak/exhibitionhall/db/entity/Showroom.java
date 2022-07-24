package com.matsak.exhibitionhall.db.entity;

public class Showroom {
    private int id;
    private String srName;
    private double area;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSrName() {
        return srName;
    }

    public void setSrName(String sr_name) {
        this.srName = sr_name;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        if (area <= 0) throw new IllegalArgumentException("Area of the showroom cannot be nagative or 0");
        this.area = area;
    }
}
