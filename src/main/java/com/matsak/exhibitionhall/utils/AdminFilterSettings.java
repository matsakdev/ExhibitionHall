package com.matsak.exhibitionhall.utils;

public class AdminFilterSettings {
    boolean showCompleted;
    boolean selectCurrent;
    int rowsAmount;
    String search;
    int page;

    public AdminFilterSettings(int page, boolean showCompleted, boolean selectCurrent, int rowsAmount, String search) {
        this.page = page;
        this.showCompleted = showCompleted;
        this.selectCurrent = selectCurrent;
        this.rowsAmount = rowsAmount;
        this.search = search;
    }

    public boolean isShowCompleted() {
        return showCompleted;
    }

    public boolean isSelectCurrent() {
        return selectCurrent;
    }

    public int getRowsAmount() {
        return rowsAmount;
    }

    public String getSearch() {
        return search;
    }

    public int getPage() {
        return page;
    }
}
