package com.cab.booking.pricingstrategy;

import com.cab.booking.model.Cab;
import com.cab.booking.model.Location;
import org.springframework.stereotype.Service;

@Service
public class PricePerKilometerStrategy implements PricingStrategy {
    @Override
    public Double calculatePrice(Cab cab, Location fromPoint, Location toPoint) {
        return fromPoint.getDistance(toPoint) * cab.getType().getCommission() * 10;
    }
}
