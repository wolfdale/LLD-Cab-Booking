package com.cab.booking.services;

import com.cab.booking.Exception.CabNotAvailableException;
import com.cab.booking.cabmatchingstrategy.CabMatchingStrategy;
import com.cab.booking.model.*;
import com.cab.booking.pricingstrategy.PricingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TripsService {
    Map<String, List<Trip>> allTrips = new HashMap<>();
    private CabService cabService;
    private RiderService riderService;
    private PricingStrategy pricingStrategy;
    private CabMatchingStrategy cabMatchingStrategy;

    @Autowired
    public TripsService(CabService cabService, RiderService riderService,
            PricingStrategy pricingStrategy, CabMatchingStrategy cabMatchingStrategy) {
        this.cabService = cabService;
        this.riderService = riderService;
        this.pricingStrategy = pricingStrategy;
        this.cabMatchingStrategy = cabMatchingStrategy;
    }

    public void endTrip(Cab cab, Location location) {
        if (cab.getTrip() != null) {
            Trip trip = cab.getTrip();
            trip.setStatus(TripStatus.COMPLETED);
        }
        cab.setLocation(location);
    }

    public List<Trip> fetchPastBookings(String id) {
        if(!allTrips.containsKey(id)) {
           return Collections.emptyList();
        }

        return allTrips.get(id);
    }

    public Cab book(String riderId, Location to, Location from) throws CabNotAvailableException{
        // get all cabs
        // shortlist available cabs
        // get price
        Rider rider = riderService.getRider(riderId);
        List<Cab> cabAtLocation = cabService.getAllCabsAtLocation(from);
        Cab cab = cabMatchingStrategy.matchCab(riderId, cabAtLocation, to, from);
        if(cab == null) {
            throw new CabNotAvailableException();
        }
        double price = pricingStrategy.calculatePrice(to, from);
        Trip trip = new Trip(cab, rider, price, to, from, TripStatus.IN_PROGRESS);
        cab.setTrip(trip);

        if(!allTrips.containsKey(riderId)) {
            // rider 1st ever trip
            allTrips.put(riderId, new ArrayList<>());
        }
        allTrips.get(riderId).add(trip);
        return cab;
    }
}