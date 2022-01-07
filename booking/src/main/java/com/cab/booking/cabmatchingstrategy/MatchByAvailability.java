package com.cab.booking.cabmatchingstrategy;

import com.cab.booking.model.Cab;
import com.cab.booking.model.Location;
import com.cab.booking.model.TripStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchByAvailability implements CabMatchingStrategy {

    @Override
    public Cab matchCab(String riderId, List<Cab> availableCabs, Location fromPoint, Location toPoint) {
        if(availableCabs == null || availableCabs.size() <= 0) {
            return null;
        }

        List<Cab> cabsNotOnTrips = availableCabs.stream()
                .filter(cab -> cab.isAvailable() &&
                        (cab.getTrip() == null || cab.getTrip().getStatus() == TripStatus.COMPLETED))
                .collect(Collectors.toList());

        if (cabsNotOnTrips == null || cabsNotOnTrips.size() == 0) {
            return null;
        }

        return cabsNotOnTrips.get(0);
    }
}
