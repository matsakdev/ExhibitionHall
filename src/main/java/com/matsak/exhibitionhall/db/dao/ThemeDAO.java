package com.matsak.exhibitionhall.db.dao;


import com.matsak.exhibitionhall.db.entity.Theme;

import java.util.List;

public interface ThemeDAO {
    void create(String theme);
    List<Theme> getAllThemes();
    Theme getById(int id);
}
