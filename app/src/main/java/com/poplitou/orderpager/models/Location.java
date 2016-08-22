package com.poplitou.orderpager.models;

/**
 * Represents a device location in the database
 */
public class Location {

    public String name;
    public boolean done;

    public Location() {}
    public Location(String name, boolean done) {
        this.name = name;
        this.done = done;
    }

}
