package com.cab.booking.pricingstrategy;

import com.cab.booking.model.Location;
import org.springframework.stereotype.Service;

@Service
public class PricePerKilometer implements PricingStrategy {
    @Override
    public Double calculatePrice(Location fromPoint, Location toPoint) {
        return fromPoint.getDistance(toPoint) * 10;
    }
}
