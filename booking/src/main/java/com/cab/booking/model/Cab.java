package com.cab.booking.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Cab {
    private String id;
    private String driverName;
    private boolean isAvailable;
    private Trip trip;
    private Location location;

    public Cab(String id, String driverName) {
        this.id = id;
        this.driverName = driverName;
        this.isAvailable = true;
    }

    public boolean isCabAtGivenLocation(Location point) {
        return this.location.getDistance(point) == 0;
    }

    @Override
    public String toString() {
        return "Cab{" +
                "id='" + id + '\'' +
                ", driverName='" + driverName + '\'' +
                ", isAvailable=" + isAvailable +
                ", trip=" + trip +
                ", location=" + location +
                '}';
    }
}
