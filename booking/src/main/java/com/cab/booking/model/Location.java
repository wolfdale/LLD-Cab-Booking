package com.cab.booking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

@ToString
@Setter
@Getter
@AllArgsConstructor
public class Location {
    private double x;
    private double y;

    public double getDistance(Location loc) {
        return sqrt( pow(this.x - loc.x, 2) + pow(this.y - loc.y, 2) );
    }
}
