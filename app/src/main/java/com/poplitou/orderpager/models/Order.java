package com.poplitou.orderpager.models;

import java.util.List;

/**
 * Represents an order in the database
 */
public class Order {

    public static final String ORDER_PATH = "orders";

    public Long timestamp;
    public String name;
    public List<Location> locations;
    public int done;

    public Order(){}

    public Order(Long timestamp, String name, List<Location> locations, int done){
        this.timestamp = timestamp;
        this.name = name;
        this.locations = locations;
        this.done = done;
    }
}
