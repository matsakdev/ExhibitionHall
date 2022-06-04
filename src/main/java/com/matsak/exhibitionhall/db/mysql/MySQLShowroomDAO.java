package com.matsak.exhibitionhall.db.mysql;

import com.matsak.exhibitionhall.db.dao.ShowroomDAO;
import com.matsak.exhibitionhall.db.entity.Showroom;

import java.util.List;

public class MySQLShowroomDAO implements ShowroomDAO {
    @Override
    public void create(String showroomName, double area) {

    }

    @Override
    public Showroom getById(int id) {
        return null;
    }

    @Override
    public List<Showroom> getAllShowrooms() {
        return null;
    }
}
