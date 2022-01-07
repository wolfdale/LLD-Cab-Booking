package com.cab.booking.pricingstrategy;

import com.cab.booking.model.Location;
import org.springframework.stereotype.Service;

@Service
public interface PricingStrategy {
    Double calculatePrice(Location fromPoint, Location toPoint);
}
