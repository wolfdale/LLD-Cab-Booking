package com.cab.booking.pricingstrategy;

/*
Fare is a factor of city specific fare, distance, time and number of active users in the system.
Fare increases as demand increases with a factor of 0.1 * total users requested rides/total inventories
when the demand crosses the supply else the fare remains same.
 */

import com.cab.booking.model.Cab;
import com.cab.booking.model.Location;
import com.cab.booking.services.CabService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class WeightedPricingStrategy implements PricingStrategy {
    public static final int HIGH_DEMAND = 3;
    private CabService cabService;

    @Autowired
    public WeightedPricingStrategy setCabService(CabService cabService) {
        this.cabService = cabService;
        return this;
    }

    @Override
    public Double calculatePrice(Cab cab, Location fromPoint, Location toPoint) {
        double price = 0;

        // We assume we supply cabs within city until cabType is OUTSTATION
        // Increase the price for OUTSTATION
        switch (cab.getType()) {
            case MICRO:
                price += 100;
                break;
            case MINI:
                price += 200;
                break;
            case SEDAN:
                price += 300;
                break;
            case SUV:
                price += 400;
                break;
            case OUTSTATION:
                price += 5000;
        }

        // Distance
        double distance = fromPoint.getDistance(toPoint);
        price += distance * cab.getType().getCommission();

        // Calculate demand
        // If available cabs are less than certain limit
        // High Demand == Higher Price (given we have limited Inventory)
        List<Cab> availableCabs = cabService.getAllCabsAtLocationByType(fromPoint, cab.getType());

        if (availableCabs.size() < HIGH_DEMAND) {
            price += 100;
        }
        return price;
    }
}
