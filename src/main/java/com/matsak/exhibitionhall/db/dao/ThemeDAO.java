package com.matsak.exhibitionhall.db.dao;


import com.matsak.exhibitionhall.db.entity.Exposition;
import com.matsak.exhibitionhall.db.entity.Theme;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ThemeDAO {
    void create(String theme);
    List<Theme> getAllThemes();
    Theme getById(int id);
    Map<Long, Set<Theme>> getThemesByExpositions(List<Exposition> expositions);
}
