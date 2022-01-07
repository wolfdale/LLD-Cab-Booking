package com.cab.booking.cabmatchingstrategy;

import com.cab.booking.model.Cab;
import com.cab.booking.model.Location;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CabMatchingStrategy {
    Cab matchCab(String riderId, List<Cab> availableCabs, Location fromPoint, Location toPoint);
}
