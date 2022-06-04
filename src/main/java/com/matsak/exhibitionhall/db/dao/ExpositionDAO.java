package com.matsak.exhibitionhall.db.dao;

import com.matsak.exhibitionhall.db.entity.Exposition;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface ExpositionDAO {
    void create(String expName, Timestamp expStartDate, Timestamp expFinalDate, double price);
    void create(String expName, Timestamp expStartDate, Timestamp expFinalDate, double price, String author);
    List<Exposition> getAllExpositions();
    List<Exposition> getAllExpositions(String order, int begin, int end) throws SQLException;
    List<Exposition> getCurrentExpositions();

}
