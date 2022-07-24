package com.matsak.exhibitionhall.db.dao;

import com.matsak.exhibitionhall.db.entity.Showroom;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

public interface ShowroomDAO {
    void create(String showroomName, double area);
    Showroom getById(int id);
    List<Showroom> getAllShowrooms();
    boolean isAvailable(int Id, Timestamp startDate, Timestamp endDate);
    Set<Showroom> getByExposition(long id);
}
