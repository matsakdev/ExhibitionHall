package com.matsak.exhibitionhall.listeners;

import com.matsak.exhibitionhall.db.entity.Order;

@FunctionalInterface
public interface OrderListener {
    void update(String eventType, Order order);
}
