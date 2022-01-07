package com.cab.booking.services;

import com.cab.booking.BookingApplication;
import com.cab.booking.Exception.CabNotAvailableException;
import com.cab.booking.cabmatchingstrategy.CabMatchingAlgorithm;
import com.cab.booking.cabmatchingstrategy.CabMatchingStrategy;
import com.cab.booking.cabmatchingstrategy.MatchByAvailability;
import com.cab.booking.model.*;
import com.cab.booking.pricingstrategy.PriceCalculationAlgorithm;
import com.cab.booking.pricingstrategy.WeightedPricingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TripsService {
    public static final int CAB_SEARCH_RADIUS = 10;

    Map<String, List<Trip>> allTrips = new HashMap<>();
    private CabService cabService;
    private RiderService riderService;

    @Autowired
    public TripsService(CabService cabService, RiderService riderService) {
        this.cabService = cabService;
        this.riderService = riderService;
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

    public Cab book(String riderId, Location to, Location from, CabType cabType) throws CabNotAvailableException{
        Rider rider = riderService.getRider(riderId);

        // Cab Matching Logic
        List<Cab> filteredCabs = cabService.getAllCabsAtLocationByType(from, cabType);
        CabMatchingAlgorithm cma = new CabMatchingAlgorithm(new MatchByAvailability());
        Cab cab = cma.executeCabMatchingAlgo(riderId, filteredCabs, from, to);

        if(cab == null) {
            throw new CabNotAvailableException();
        }

        // Price calculation logic
        PriceCalculationAlgorithm pca = new PriceCalculationAlgorithm(new WeightedPricingStrategy()
                .setCabService(cabService));
        double price = pca.calculatePrice(cab, to, from);

        // Book cab & start trip
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