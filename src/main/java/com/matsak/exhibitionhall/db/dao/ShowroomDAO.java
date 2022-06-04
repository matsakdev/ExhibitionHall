package com.matsak.exhibitionhall.db.dao;

import com.matsak.exhibitionhall.db.entity.Showroom;

import java.util.List;

public interface ShowroomDAO {
    void create(String showroomName, double area);
    Showroom getById(int id);
    List<Showroom> getAllShowrooms();

}
