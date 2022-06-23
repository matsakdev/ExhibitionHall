package com.matsak.exhibitionhall.db.dao;

import com.matsak.exhibitionhall.db.entity.Exposition;
import com.matsak.exhibitionhall.db.entity.FilterSettings;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ExpositionDAO {
    void create(String expName, Timestamp expStartDate, Timestamp expFinalDate, double price);
    void create(String expName, Timestamp expStartDate, Timestamp expFinalDate, double price, String author);
    List<Exposition> getAllExpositions();
    List<Exposition> getAllExpositions(String order, int begin, int end, FilterSettings filters) throws SQLException;
    List<Exposition> getCurrentExpositions();
    Exposition getById(long id) throws SQLException;
    boolean isImagePathAvailable(String fileName);
    Map<Integer, Integer> ticketsByExpositions();

    boolean UpdateExposition(Exposition updatedExposition, Set<Integer> selectedShowrooms);
}
