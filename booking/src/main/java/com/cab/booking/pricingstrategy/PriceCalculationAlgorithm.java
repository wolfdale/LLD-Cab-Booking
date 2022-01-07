package com.cab.booking.pricingstrategy;

import com.cab.booking.model.Cab;
import com.cab.booking.model.Location;

public class PriceCalculationAlgorithm {
    private PricingStrategy pricingStrategy;

    public PriceCalculationAlgorithm(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public double calculatePrice(Cab cab, Location from, Location to) {
        return pricingStrategy.calculatePrice(cab, from, to);
    }
}
