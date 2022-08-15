package com.matsak.exhibitionhall.db.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FilterSettings {
    private String startDate;
    private String endDate;
    private List<Theme> themes;
    private String search;
    private boolean isAdmin;
    private boolean showCompleted = false;

    public boolean isShowCompleted() {
        return showCompleted;
    }

    public void setShowCompleted(boolean showCompleted) {
        this.showCompleted = showCompleted;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        if (startDate == null || startDate.equals(""))  this.startDate = null;
        try {
            this.startDate = Timestamp.valueOf(LocalDateTime.parse(startDate, dateFormat)).toString();
        } catch (Exception e) {
            this.startDate = null;
        }
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        if (endDate == null || endDate.equals(""))  this.endDate = null;
        try {
            this.endDate = Timestamp.valueOf(LocalDateTime.parse(endDate, dateFormat).toString()).toString();
        } catch (Exception e) {
            this.endDate = null;
        }
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
