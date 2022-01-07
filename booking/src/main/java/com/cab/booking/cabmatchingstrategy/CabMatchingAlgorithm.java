package com.cab.booking.cabmatchingstrategy;

import com.cab.booking.model.Cab;
import com.cab.booking.model.Location;

import java.util.List;

public class CabMatchingAlgorithm {
    private CabMatchingStrategy cabMatchingStrategy;

    public CabMatchingAlgorithm(CabMatchingStrategy cabMatchingStrategy) {
        this.cabMatchingStrategy = cabMatchingStrategy;
    }

    public Cab executeCabMatchingAlgo(String riderId, List<Cab> availableCabs,
            Location fromPoint, Location toPoint) {
        return cabMatchingStrategy.matchCab(riderId, availableCabs, fromPoint, toPoint);
    }
}
