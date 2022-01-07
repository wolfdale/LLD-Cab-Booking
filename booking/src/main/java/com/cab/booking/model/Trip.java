package com.cab.booking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Trip {
    private Cab cab;
    private Rider rider;
    private double price;
    private Location toPoint;
    private Location fromPoint;
    private TripStatus status;

}
