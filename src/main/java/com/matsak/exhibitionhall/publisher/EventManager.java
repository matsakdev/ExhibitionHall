package com.matsak.exhibitionhall.publisher;

import com.matsak.exhibitionhall.db.entity.Order;
import com.matsak.exhibitionhall.listeners.OrderListener;
import jdk.jfr.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {
    Map<String, List<OrderListener>> listeners = new HashMap<>();

    public EventManager(String... operations) {
        for (String operation : operations) {
            this.listeners.put(operation, new ArrayList<>());
        }
    }

    public void subscribe(String eventType, OrderListener listener) {
        List<OrderListener> users = listeners.get(eventType);
        users.add(listener);
    }

    public void unsubscribe(String eventType, OrderListener listener) {
        List<OrderListener> users = listeners.get(eventType);
        users.remove(listener);
    }

    public void notify(String eventType, Order order) {
        List<OrderListener> users = listeners.get(eventType);
        for (OrderListener listener : users) {
            listener.update(eventType, order);
        }
    }
}
