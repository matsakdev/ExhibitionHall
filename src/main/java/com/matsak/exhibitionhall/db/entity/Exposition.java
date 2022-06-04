package com.matsak.exhibitionhall.db.entity;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Set;

public class Exposition {
    private long id;
    private String expName;
    private Timestamp expStartDate;
    private Timestamp expFinalDate;
    private Time expStartTime;
    private Time expEndTime;
    private Set<Showroom> relatedShowrooms;

    public Time getExpStartTime() {
        return expStartTime;
    }

    public void setExpStartTime(Time expStartTime) {
        this.expStartTime = expStartTime;
    }

    public Time getExpEndTime() {
        return expEndTime;
    }

    public void setExpEndTime(Time expEndTime) {
        this.expEndTime = expEndTime;
    }

    private double price;
    private String author;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public Timestamp getExpStartDate() {
        return expStartDate;
    }

    public void setExpStartDate(Timestamp expStartDate) {
        this.expStartDate = expStartDate;
    }

    public Timestamp getExpFinalDate() {
        return expFinalDate;
    }

    public void setExpFinalDate(Timestamp expFinalDate) {
        this.expFinalDate = expFinalDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Exposition{" +
                "id=" + id +
                ", expName='" + expName + '\'' +
                ", expStartDate=" + expStartDate +
                ", expFinalDate=" + expFinalDate +
                ", expStartTime=" + expStartTime +
                ", expEndTime=" + expEndTime +
                ", relatedShowrooms=" + relatedShowrooms +
                ", price=" + price +
                ", author='" + author + '\'' +
                '}';
    }
}
