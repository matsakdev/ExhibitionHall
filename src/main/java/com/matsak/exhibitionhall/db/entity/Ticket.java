package com.matsak.exhibitionhall.db.entity;

import java.sql.Timestamp;
import java.util.Date;

public class Ticket {
    private long num;
    private Timestamp order_date;
    private long exposition_id;
    private String user_login;

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public Timestamp getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Timestamp order_date) {
        this.order_date = order_date;
    }

    public long getExposition_id() {
        return exposition_id;
    }

    public void setExposition_id(long exposition_id) {
        this.exposition_id = exposition_id;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }
}
