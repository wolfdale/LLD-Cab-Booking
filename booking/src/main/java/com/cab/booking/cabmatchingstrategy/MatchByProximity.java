package com.cab.booking.cabmatchingstrategy;

import com.cab.booking.model.Cab;
import com.cab.booking.model.Location;

import java.util.List;

public class MatchByProximity implements CabMatchingStrategy{

    @Override
    public Cab matchCab(String riderId, List<Cab> availableCabs, Location fromPoint, Location toPoint) {
        return null;
        // Use some kind of radius for proximity calculation
    }
}
